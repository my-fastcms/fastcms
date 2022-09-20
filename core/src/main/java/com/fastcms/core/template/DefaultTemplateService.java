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
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class DefaultTemplateService<T extends TreeNode> implements TemplateService, TreeNodeConvert<T>, InitializingBean {

    private String templateDir = "./htmls";

    private Map<String, Template> templateMap = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    private Environment environment;

    private TemplateFinder templateFinder;

    @Autowired
    private IConfigService configService;

    public static ResourceHandlerRegistry resourceHandlerRegistry;

    public DefaultTemplateService() {
        templateFinder = new DefaultTemplateFinder();
    }

    @Override
    public void initialize() {
        templateMap.clear();

        try {
            List<Path> collect = Files.list(getTemplateRootPath()).collect(Collectors.toList());
            collect.forEach(item -> {
                if(Files.isDirectory(item)) {
                    Template template = templateFinder.find(item);
                    if(template != null) {
                        templateMap.putIfAbsent(template.getId(), template);
                    }
                }
            });

            List<Template> templateList = getTemplateList();
            if(templateList != null && templateList.size() > 0) {
                Config config = configService.findByKey(FastcmsConstants.TEMPLATE_ENABLE_ID);
                if(config == null) {
                    configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, templateList.get(0).getId());
                }

                if (resourceHandlerRegistry != null) {

/*
                    Set<String> locations = new HashSet<>();
                    locations.add("classpath:/static/");
                    for (Template template : templateList) {
                        locations.add(ResourceUtils.FILE_URL_PREFIX + templateDir + template.getPath() + "/static/");
                    }

                    resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations(locations.toArray(new String[]{}));

                    HandlerMapping resourceHandlerMapping = ApplicationUtils.getBean("resourceHandlerMapping", HandlerMapping.class);
                    ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(SimpleUrlHandlerMapping.class, "registerHandlers"), resourceHandlerMapping);
*/

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
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
            //#I4NI6J https://gitee.com/xjd2020/fastcms/issues/I4NI6J
            List<Template> templateList = new ArrayList<>(templateMap.values());
            Template template = templateList != null && templateList.size()>0 ? templateList.get(0) : null;
            if(template == null) return null;
            config = configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, template.getId());
        }
        return getTemplate(config.getValue());
    }

    @Override
    public List<Template> getTemplateList() {
        ArrayList<Template> templates = new ArrayList<>(templateMap.values());
        templates.forEach(item -> {
            if(getCurrTemplate() != null && item.getId().equals(getCurrTemplate().getId())) {
                item.setActive(true);
            }else {
                item.setActive(false);
            }
        });
        return templates;
    }

    @Override
    public void install(File file) throws Exception {
        String name = file.getName();
        name = name.substring(0, name.lastIndexOf("."));
        final String path = "/".concat(name).concat("/");

        if(checkPath(path)) {
            throw new RuntimeException("模板[" + path + "]已存在");
        }

        FileUtils.unzip(file.toPath(), DirUtils.getTemplateDir());

        //check properties
        String templatePath = getTemplateRootPath().toString().concat(path);
        Template template = templateFinder.find(Paths.get(templatePath));
        if (template == null || StringUtils.isBlank(template.getId()) || StringUtils.isBlank(template.getPath())) {
            //上传的zip文件包不符合规范 删除
            org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(templatePath).toFile());
            throw new RuntimeException("模板[" + path + "]缺少必要文件或者属性");
        }

        initialize();
    }

    boolean checkPath(String uploadPath) {
        for (Template template : getTemplateList()) {
            if(template.getPath().equals(uploadPath)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void unInstall(String templateId) throws Exception {
        Template template = getTemplate(templateId);
        if(template == null) {
            throw new FastcmsException(FastcmsException.INVALID_PARAM, "模板不存在");
        }

        Template currTemplate = getCurrTemplate();
        if(currTemplate != null && templateId.equals(currTemplate.getId())) {
            throw new FastcmsException(FastcmsException.CLIENT_INVALID_PARAM, "正在使用中的模板不能卸载");
        }
        org.apache.commons.io.FileUtils.deleteDirectory(template.getTemplatePath().toFile());
        initialize();
    }

    @Override
    public List<FileTreeNode> getTemplateTreeFiles() throws IOException {

        Template currTemplate = getCurrTemplate();
        if(currTemplate == null) return null;

        List<FileTreeNode> treeNodeList = Files.walk(currTemplate.getTemplatePath()).filter(item -> !item.toString().endsWith(".properties"))
                .map(item -> new FileTreeNode(item))
                .sorted(Comparator.comparing(FileTreeNode::getSortNum)).collect(Collectors.toList());
        return (List<FileTreeNode>) getTreeNodeList(treeNodeList);
    }

    @Override
    public T convert2Node(Object object) {
        Template currTemplate = getCurrTemplate();
        FileTreeNode fileTreeNode = (FileTreeNode) object;
        fileTreeNode.setFilePath(fileTreeNode.getPath().substring(fileTreeNode.getPath().lastIndexOf(currTemplate.getPathName())).replaceAll("\\\\", "/"));
        return (T) fileTreeNode;
    }

    @Override
    public boolean isParent(T node) {
        return ((FileTreeNode) node).getPath().equals(getCurrTemplate().getTemplatePath().toString());
    }

    @Override
    public boolean accept(T node1, T node2) {
        return Objects.equals(((FileTreeNode)node1).getParent(), ((FileTreeNode)node2).getPath());
    }

    Path getTemplateRootPath() {
        String[] activeProfiles = environment.getActiveProfiles();
        String profile = activeProfiles == null || activeProfiles.length <=0 ? FastcmsConstants.DEV_MODE : activeProfiles[0];

        Path rootPath;
        if(FastcmsConstants.DEV_MODE.equals(profile)) {
            String path = getClass().getResource("/").getPath() + templateDir;
            rootPath = Paths.get(path.substring(1));
        }else {
            rootPath = Paths.get(DirUtils.getTemplateDir());
        }
        return rootPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

}
