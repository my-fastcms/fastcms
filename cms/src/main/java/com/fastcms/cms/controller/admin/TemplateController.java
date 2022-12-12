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
package com.fastcms.cms.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fastcms.cms.entity.Menu;
import com.fastcms.cms.service.IMenuService;
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.common.utils.FileUtils;
import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import com.fastcms.service.IConfigService;
import com.fastcms.utils.ApplicationUtils;
import com.fastcms.utils.I18nUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fastcms.common.constants.FastcmsConstants.FASTCMS_SYSTEM_ERROR;
import static com.fastcms.core.template.TemplateService.TemplateI18n.*;
import static com.fastcms.service.IAttachmentService.AttachmentI18n.ATTACHMENT_FILE_UPLOAD_LIST_FAIL;
import static com.fastcms.service.IResourceService.ResourceI18n.*;

/**
 * 模板管理
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private IMenuService menuService;

    /**
     * 模板列表
     * @return
     */
    @GetMapping("list")
    @Secured(name = RESOURCE_NAME_TEMPLATE_LIST, resource = "templates:list", action = ActionTypes.READ)
	public RestResult<List<Template>> list() {
        return RestResultUtils.success(templateService.getTemplateList());
    }

    /**
     * 获取当前模板
     * @return
     */
    @GetMapping("current")
	public RestResult<Template> getCurrTemplate() {
        return RestResultUtils.success(templateService.getCurrTemplate());
    }

    /**
     * 安装模板
     * @param file
     * @return
     */
    @PostMapping("install")
    @Secured(name = RESOURCE_NAME_TEMPLATE_INSTALL, resource = "templates:install", action = ActionTypes.WRITE)
	public Object install(@RequestParam("file") MultipartFile file) {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_DEV_NOT_ALLOW_INSTALL));
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName) || !".zip".equalsIgnoreCase(suffixName)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_ZIP_INSTALL));
        }

        File uploadFile = new File(DirUtils.getTemplateDir(), file.getOriginalFilename());
        try {
            file.transferTo(uploadFile);
            templateService.install(uploadFile);
            return RestResultUtils.success();
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage() == null ? I18nUtils.getMessage(FASTCMS_SYSTEM_ERROR) : e.getMessage());
        } finally {
            if(uploadFile != null) {
                uploadFile.delete();
            }
        }
    }

    /**
     * 卸载模板
     * @param templateId    模板id
     * @return
     */
    @PostMapping("unInstall/{templateId}")
    @Secured(name = RESOURCE_NAME_TEMPLATE_UNINSTALL, resource = "templates:unInstall", action = ActionTypes.WRITE)
	public Object unInstall(@PathVariable("templateId") String templateId) {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_DEV_NOT_ALLOW_UNINSTALL));
        }

        try {
            templateService.unInstall(templateId);
            return RestResultUtils.success();
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage());
        }

    }

    /**
     * 获取模板文件树
     * @return
     */
    @GetMapping("files/tree/list")
    @Secured(name = RESOURCE_NAME_TEMPLATE_FILE_LIST, resource = "templates:files/tree/list", action = ActionTypes.READ)
	public Object treeList() {
        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_NOT_EXIST));
        }

        try {
            return RestResultUtils.success(templateService.getTemplateTreeFiles());
        } catch (Exception e) {
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 获取文件内容
     * @param filePath
     * @return
     */
    @GetMapping("files/get")
    @Secured(name = RESOURCE_NAME_TEMPLATE_FILE_INFO, resource = "templates:files/get", action = ActionTypes.READ)
	public Object getFileContent(@RequestParam("filePath") String filePath) {

        if (StringUtils.isBlank(filePath) || filePath.contains("..")) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_NOT_EXIST));
        }

        Template currTemplate = templateService.getCurrTemplate();
        if (currTemplate == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_DEFAULT_NOT_EXIST));
        }

        String suffix = filePath.substring(filePath.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_SUFFIX_NOT_NULL));
        }

        if (!Arrays.asList(".html", ".js", ".css", ".txt").contains(suffix)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_NOT_SUPPORT_EDIT));
        }

        Path file = getFilePath(filePath);
        if (Files.isDirectory(file)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_SURE_ONE));
        }

        if (!Files.exists(file)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_NOT_EXIST));
        }

        try {
            return RestResultUtils.success(FileCopyUtils.copyToString(new FileReader(file.toFile())));
        } catch (Exception e) {
            e.printStackTrace();
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 激活模板
     * @param templateId    模板id
     * @return
     */
    @PostMapping("enable/{templateId}")
    @Secured(name = RESOURCE_NAME_TEMPLATE_ENABLE, resource = "templates:enable", action = ActionTypes.WRITE)
	public Object enable(@PathVariable("templateId") String templateId) {
        configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, templateId);
        return RestResultUtils.success();
    }

    /**
     * 保存模板
     * @param filePath          文件
     * @param fileContent       文件内容
     * @return
     * @throws IOException
     */
    @PostMapping("file/save")
    @Secured(name = RESOURCE_NAME_TEMPLATE_FILE_SAVE, resource = "templates:file/save", action = ActionTypes.WRITE)
	public Object save(@RequestParam("filePath") String filePath, @RequestParam("fileContent") String fileContent) {
        if(StringUtils.isBlank(filePath) || filePath.contains("..")) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_NOT_EXIST));
        }

        if(StringUtils.isBlank(fileContent)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_CONTENT_NOT_NULL));
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_NOT_EXIST));
        }

        Path currFile = getFilePath(filePath);

        if(currFile == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_NOT_EXIST));
        }

        File file = currFile.toFile();
        if(!file.canWrite()) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_NOT_WRITE_AUTH));
        }

        FileUtils.writeString(file, fileContent);

        return RestResultUtils.success();
    }

    /**
     * 上传模板文件
     * @param dirName
     * @param files
     * @return
     */
    @PostMapping("files/upload")
    @ExceptionHandler(value = MultipartException.class)
    @Secured(name = RESOURCE_NAME_TEMPLATE_FILE_UPLOAD, resource = "templates:files/upload", action = ActionTypes.WRITE)
	public Object upload(String dirName, @RequestParam("files") MultipartFile files[]) {

        if(StringUtils.isBlank(dirName) || dirName.contains("..")) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_UPLOAD_DIR_NOT_NULL));
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_NOT_EXIST));
        }

        if(files == null || files.length <=0) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_UPLOAD_EMPTY));
        }

        Path templatePath = getFilePath(dirName);
        if(templatePath == null || !Files.exists(templatePath)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_UPLOAD_DIR_NOT_EXIST));
        }

        List<String> errorFiles = new ArrayList<>();

        for(MultipartFile file : files) {

            if(FileUtils.isNotAllowFile(file.getOriginalFilename())) {
                continue;
            }

            File uploadFile = new File(templatePath.toString(), file.getOriginalFilename());
            try {
                if (!uploadFile.getParentFile().exists()) {
                    uploadFile.getParentFile().mkdirs();
                }
                file.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
                if(uploadFile != null) {
                    uploadFile.delete();
                }
                errorFiles.add(file.getOriginalFilename());
            }
        }

        return errorFiles.isEmpty() ? RestResultUtils.success()
                : RestResultUtils.failed(errorFiles.stream().collect(Collectors.joining(",")).concat(I18nUtils.getMessage(ATTACHMENT_FILE_UPLOAD_LIST_FAIL)));
    }

    /**
     * 删除模板文件
     * @param filePath
     * @return
     */
    @PostMapping("file/delete")
    @Secured(name = RESOURCE_NAME_TEMPLATE_FILE_DELETE, resource = "templates:file/delete", action = ActionTypes.WRITE)
	public Object delFile(@RequestParam("filePath") String filePath) {

        if(StringUtils.isBlank(filePath)) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_PATH_IS_EMPTY));
        }

        if(filePath.contains("..")) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_PATH_IS_ERROR));
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_NOT_EXIST));
        }
        try {
            Path templateFilePath = getFilePath(filePath);

            if(Files.isDirectory(templateFilePath)) {
                return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_FILE_PATH_IS_NOT_ALLOW_DELETE));
            }

            templateFilePath.toFile().delete();
            return RestResultUtils.success();
        } catch (Exception e) {
            e.printStackTrace();
            return RestResultUtils.failed(e.getMessage());
        }
    }

    /**
     * 菜单列表
     * @return
     */
    @RequestMapping("menu/list")
    @Secured(name = RESOURCE_NAME_TEMPLATE_MENU_LIST, resource = "templates:menu/list", action = ActionTypes.READ)
	public RestResult<List<IMenuService.MenuNode> > menuList() {
        return RestResultUtils.success(menuService.getMenus());
    }

    /**
     * 菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping("menu/get/{menuId}")
    @Secured(name = RESOURCE_NAME_TEMPLATE_MENU_INFO, resource = "templates:menu/get", action = ActionTypes.READ)
	public RestResult<Menu> getMenu(@PathVariable("menuId") Long menuId) {
        return RestResultUtils.success(menuService.getById(menuId));
    }

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @PostMapping("menu/save")
    @Secured(name = RESOURCE_NAME_TEMPLATE_MENU_SAVE, resource = "templates:menu/save", action = ActionTypes.WRITE)
	public RestResult<Boolean> saveMenu(@Validated Menu menu) {
        return RestResultUtils.success(menuService.saveOrUpdate(menu));
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @PostMapping("menu/delete/{menuId}")
    @Secured(name = RESOURCE_NAME_TEMPLATE_MENU_DELETE, resource = "templates:menu/delete", action = ActionTypes.WRITE)
	public RestResult<Boolean> doDeleteMenu(@PathVariable("menuId") Long menuId) {

        List<Menu> list = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, menuId));
        if(list != null && !list.isEmpty()) {
            return RestResultUtils.failed(I18nUtils.getMessage(CMS_TEMPLATE_MENU_CHILDREN_IS_NOT_DELETE));
        }

        return RestResultUtils.success(menuService.removeById(menuId));
    }

    Path getFilePath(String filePath) {
        Template currTemplate = templateService.getCurrTemplate();
        return Paths.get(currTemplate.getTemplatePath().toString().concat(filePath.substring(currTemplate.getPathName().length())));
    }

}
