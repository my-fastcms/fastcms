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
import com.fastcms.common.exception.I18nFastcmsException;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.common.utils.StrUtils;
import com.fastcms.entity.Config;
import com.fastcms.service.IConfigService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.CollectionUtils;
import com.fastcms.utils.I18nUtils;
import com.fastcms.utils.ReflectUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static com.fastcms.core.template.TemplateService.TemplateI18n.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Service
public class DefaultTemplateService<T extends TreeNode> implements TemplateService, TreeNodeConvert<T>, InitializingBean {

//    private String templateDir = "./htmls";

    private String i18nDir = "i18n";

    private Map<String, Template> templateMap = Collections.synchronizedMap(new HashMap<>());

//    @Autowired
//    private Environment environment;

    @Autowired
    private TemplateFinder templateFinder;

    @Autowired
    private IConfigService configService;

    @Override
    public void initialize() throws IOException {
        templateMap.clear();

        List<Path> collect = Files.list(getTemplateRootPath()).collect(Collectors.toList());
        collect.forEach(item -> {
            if(Files.isDirectory(item)) {
                Template template = templateFinder.find(item);
                if(template != null) {
                    templateMap.putIfAbsent(template.getId(), template);
                }
            }
        });

        setDefaultTemplate();
    }

    /**
     * 运行时刷新静态资源目录
     */
    private void refreshStaticMapping() throws Exception {

        final HandlerMapping resourceHandlerMapping = ApplicationUtils.getBean("resourceHandlerMapping", HandlerMapping.class);
        final Map<String, Object> handlerMap = (Map<String, Object>) ReflectUtil.getFieldValue(resourceHandlerMapping, "handlerMap");
        handlerMap.remove("/**");
        getTemplateList().forEach(item -> handlerMap.remove(item.getPath().concat("**")));

        final UrlPathHelper mvcUrlPathHelper = ApplicationUtils.getBean("mvcUrlPathHelper", UrlPathHelper.class);
        final ContentNegotiationManager mvcContentNegotiationManager = ApplicationUtils.getBean("mvcContentNegotiationManager", ContentNegotiationManager.class);
        final ServletContext servletContext = ApplicationUtils.getBean(ServletContext.class);

        final ResourceHandlerRegistry resourceHandlerRegistry = new ResourceHandlerRegistry(ApplicationUtils.getApplicationContext(), servletContext, mvcContentNegotiationManager, mvcUrlPathHelper);

        final String uploadDir = DirUtils.getUploadDir();
        final String templateDir = DirUtils.getTemplateDir();
        Set<String> locations = new HashSet<>();
        locations.add(ResourceUtils.CLASSPATH_URL_PREFIX + FastcmsConstants.TEMPLATE_STATIC);
        locations.add(ResourceUtils.FILE_URL_PREFIX + uploadDir);
        resourceHandlerRegistry.addResourceHandler("/**").addResourceLocations(locations.toArray(new String[]{}));
        for (Template template : getTemplateList()) {
            locations = new HashSet<>();
            locations.add(ResourceUtils.FILE_URL_PREFIX + templateDir + template.getPath() + FastcmsConstants.TEMPLATE_STATIC);
            resourceHandlerRegistry.addResourceHandler(template.getPath().concat("**")).addResourceLocations(locations.toArray(new String[]{}));
        }

        SimpleUrlHandlerMapping simpleUrlHandlerMapping = (SimpleUrlHandlerMapping) ReflectUtil.invokeMethod(resourceHandlerRegistry, "getHandlerMapping");
        Method registerHandlers = ReflectionUtils.findMethod(SimpleUrlHandlerMapping.class, "registerHandlers", Map.class);
        ReflectUtil.invokeMethod(resourceHandlerMapping, registerHandlers, simpleUrlHandlerMapping.getUrlMap());

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
            Template template = templateList != null && templateList.size() > 0 ? templateList.get(0) : null;
            if(template == null) return null;
            config = configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, template.getId());
        }
        return getTemplate(config.getValue());
    }

    @Override
    public void setDefaultTemplate() {
        List<Template> templateList = getTemplateList();
        if(CollectionUtils.isNotEmpty(templateList)) {
            String config = configService.getValue(FastcmsConstants.TEMPLATE_ENABLE_ID);
            if(StrUtils.isBlank(config) || !containsKey(config)) {
                configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, templateList.get(0).getId());
            }
        }
    }

    boolean containsKey(String templateId) {
        return templateMap.containsKey(templateId);
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
        final String path = StrUtils.SLASH.concat(name).concat(StrUtils.SLASH);

        if(checkPath(path)) {
            throw new RuntimeException(String.format(I18nUtils.getMessage(CMS_TEMPLATE_PATH_IS_EXIST), path));
        }

        String templatePath = getTemplateRootPath().toString().concat(path);

        try {
            FileUtils.unzip(file.toPath(), DirUtils.getTemplateDir());
        } catch (IOException e) {
            org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(templatePath).toFile());
            throw new RuntimeException(e.getMessage());
        }

        //check properties
        Template template = templateFinder.find(Paths.get(templatePath));
        if (template == null || StringUtils.isBlank(template.getId()) || StringUtils.isBlank(template.getPath())) {
            //上传的zip文件包不符合规范 删除
            org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(templatePath).toFile());
            throw new RuntimeException(String.format(I18nUtils.getMessage(CMS_TEMPLATE_PATH_MISSING_REQUIRED_ATTR), path));
        }

        try {
            initialize();
            refreshStaticMapping();
            //设置i18n
            ApplicationUtils.getBean(ReloadableResourceBundleMessageSource.class).setBasenames(getI18nNames());
        } catch (Exception e) {
            org.apache.commons.io.FileUtils.deleteDirectory(Paths.get(templatePath).toFile());
            templateMap.remove(template.getId());
            throw new RuntimeException(e.getMessage());
        }

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
            throw new I18nFastcmsException(CMS_TEMPLATE_NOT_EXIST);
        }

        Template currTemplate = getCurrTemplate();
        if(currTemplate != null && templateId.equals(currTemplate.getId())) {
            throw new I18nFastcmsException(CMS_TEMPLATE_USING_IS_NOT_ALLOW_UNINSTALL);
        }

        try {
            refreshStaticMapping();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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
    public String[] getI18nNames() {
        List<String> namesList = new ArrayList<>();
        List<Template> templateList = getTemplateList();
        for (Template template : templateList) {
            Path i18nPath = template.getTemplatePath().resolve(i18nDir);
            if (i18nPath.toFile().isDirectory() && i18nPath.toFile().exists()) {
                String i18n = ResourceUtils.FILE_URL_PREFIX.concat(i18nPath.toString().concat(StrUtils.SLASH).concat(template.getI18n()));
                namesList.add(i18n);
            }
        }
        namesList.add(ResourceUtils.CLASSPATH_URL_PREFIX.concat("i18n/message"));
        return namesList.toArray(new String[]{});
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
        return Paths.get(DirUtils.getTemplateDir());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialize();
    }

}
