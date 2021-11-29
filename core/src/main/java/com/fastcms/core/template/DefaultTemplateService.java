/**
 * Copyright (c) 广州小橘灯信息科技有限公司 2016-2017, wjun_java@163.com.
 * <p>
 * Licensed under the GNU Lesser General Public License (LGPL) ,Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * http://www.xjd2020.com
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fastcms.core.template;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.core.utils.DirUtils;
import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class DefaultTemplateService implements TemplateService, InitializingBean {

    private String templateDir = "./htmls";

    private Map<String, Template> templateMap = new HashMap<>();

    @Autowired
    private Environment environment;

    private TemplateFinder templateFinder;

    @Autowired
    private IConfigService configService;

    public DefaultTemplateService() {
        templateFinder = new DefaultTemplateFinder();
    }

    @Override
    public void initialize() {
        templateMap.clear();
        String[] activeProfiles = environment.getActiveProfiles();
        String profile = activeProfiles == null || activeProfiles.length <=0 ? FastcmsConstants.DEV_MODE : activeProfiles[0];

        Path rootPath;
        if(FastcmsConstants.DEV_MODE.equals(profile)) {
            String path = getClass().getResource("/").getPath() + templateDir;
            rootPath = Paths.get(path.substring(1));
        }else {
            rootPath = Paths.get(DirUtils.getTemplateDir());
        }

        try {
            Stream<Path> list = Files.list(rootPath);

            List<Path> collect = list.collect(Collectors.toList());
            collect.forEach(item -> {
                if(Files.isDirectory(item)){
                    Template template = templateFinder.find(item);
                    if(template != null) {
                        if(templateMap.keySet().contains(template.getId()))
                            throw new RuntimeException(String.format("template id {} is exist", template.getId()));
                        templateMap.put(template.getId(), template);
                    }
                }
            });

            List<Template> templateList = getTemplateList();
            if(templateList != null && templateList.size()>0) {
                Config config = configService.findByKey(FastcmsConstants.TEMPLATE_ENABLE_ID);
                if(config == null) {
                    configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, templateList.get(0).getId());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Template getTemplate(String id) {
        return templateMap.get(id);
    }

    @Override
    public Template getCurrTemplate() {
        Config config = configService.findByKey(FastcmsConstants.TEMPLATE_ENABLE_ID);
        if(config == null) {
            List<Template> templateList = getTemplateList();
            Template template = templateList != null && templateList.size()>0 ? templateList.get(0) : null;
            if(template == null) return null;
            config = configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, template.getId());
        }
        return getTemplate(config.getValue());
    }

    @Override
    public List<Template> getTemplateList() {
        return new ArrayList<>(templateMap.values());
    }

    @Override
    public void install(File file) throws Exception {
        FileUtils.unzip(file.toPath().toString(), DirUtils.getTemplateDir());
        initialize();
    }

    @Override
    public void unInstall(String templateId) throws Exception {
        Template template = getTemplate(templateId);
        if(template == null) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "模板不存在");
        }

        Template currTemplate = getCurrTemplate();
        if(currTemplate != null && currTemplate.getId().equals(templateId)) {
            throw new FastcmsException(FastcmsException.CLIENT_INVALID_PARAM, "正在使用中的模板不能卸载");
        }
        org.apache.commons.io.FileUtils.deleteDirectory(template.getTemplatePath().toFile());
        initialize();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

}
