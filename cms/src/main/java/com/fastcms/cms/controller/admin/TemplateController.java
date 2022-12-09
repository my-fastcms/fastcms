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
    @Secured(name = "模板列表", resource = "templates:list", action = ActionTypes.READ)
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
    @Secured(name = "模板安装", resource = "templates:install", action = ActionTypes.WRITE)
	public Object install(@RequestParam("file") MultipartFile file) {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed("开发环境不允许安装模板");
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName) || !".zip".equalsIgnoreCase(suffixName)) {
            return RestResultUtils.failed("请上传zip文件");
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
    @Secured(name = "模板卸载", resource = "templates:unInstall", action = ActionTypes.WRITE)
	public Object unInstall(@PathVariable("templateId") String templateId) {

        if (ApplicationUtils.isDevelopment()) {
            return RestResultUtils.failed("开发环境不允许卸载模板");
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
    @Secured(name = "模板文件列表", resource = "templates:files/tree/list", action = ActionTypes.READ)
	public Object treeList() {
        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed("模板不存在");
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
    @Secured(name = "模板文件信息", resource = "templates:files/get", action = ActionTypes.READ)
	public Object getFileContent(@RequestParam("filePath") String filePath) {

        if (StringUtils.isBlank(filePath) || filePath.contains("..")) {
            return RestResultUtils.failed("文件不存在");
        }

        Template currTemplate = templateService.getCurrTemplate();
        if (currTemplate == null) {
            return RestResultUtils.failed("未找到目标模板");
        }

        String suffix = filePath.substring(filePath.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            return RestResultUtils.failed("文件后缀不能为空");
        }

        if (!Arrays.asList(".html", ".js", ".css", ".txt").contains(suffix)) {
            return RestResultUtils.failed("文件不支持编辑");
        }

        Path file = getFilePath(filePath);
        if (Files.isDirectory(file)) {
            return RestResultUtils.failed("请指定一个文件");
        }

        if (!Files.exists(file)) {
            return RestResultUtils.failed("文件不存在");
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
    @Secured(name = "模板启用", resource = "templates:enable", action = ActionTypes.WRITE)
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
    @Secured(name = "模板文件保存", resource = "templates:file/save", action = ActionTypes.WRITE)
	public Object save(@RequestParam("filePath") String filePath, @RequestParam("fileContent") String fileContent) {
        if(StringUtils.isBlank(filePath) || filePath.contains("..")) {
            return RestResultUtils.failed("没有找到模板");
        }

        if(StringUtils.isBlank(fileContent)) {
            return RestResultUtils.failed("文件内容不能为空");
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed("没有找到模板");
        }

        Path currFile = getFilePath(filePath);

        if(currFile == null) {
            return RestResultUtils.failed("文件不存在");
        }

        File file = currFile.toFile();
        if(!file.canWrite()) {
            return RestResultUtils.failed("文件没有写入权限");
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
    @Secured(name = "模板文件上传", resource = "templates:files/upload", action = ActionTypes.WRITE)
	public Object upload(String dirName, @RequestParam("files") MultipartFile files[]) {

        if(StringUtils.isBlank(dirName) || dirName.contains("..")) {
            return RestResultUtils.failed("请选择上传目录");
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed("没有找到模板");
        }

        if(files == null || files.length <=0) {
            return RestResultUtils.failed("请选择需要上传的模板文件");
        }

        Path templatePath = getFilePath(dirName);
        if(templatePath == null || !Files.exists(templatePath)) {
            return RestResultUtils.failed("目录不存在");
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
                : RestResultUtils.failed(errorFiles.stream().collect(Collectors.joining(",")).concat(",以上文件上传失败"));
    }

    /**
     * 删除模板文件
     * @param filePath
     * @return
     */
    @PostMapping("file/delete")
    @Secured(name = "模板文件删除", resource = "templates:file/delete", action = ActionTypes.WRITE)
	public Object delFile(@RequestParam("filePath") String filePath) {

        if(StringUtils.isBlank(filePath)) {
            return RestResultUtils.failed("文件路径为空");
        }

        if(filePath.contains("..")) {
            return RestResultUtils.failed("文件路径不合法");
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return RestResultUtils.failed("没有找到模板");
        }
        try {
            Path templateFilePath = getFilePath(filePath);

            if(Files.isDirectory(templateFilePath)) {
                return RestResultUtils.failed("不可删除文件目录");
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
    @Secured(name = "模板菜单列表", resource = "templates:menu/list", action = ActionTypes.READ)
	public RestResult<List<IMenuService.MenuNode> > menuList() {
        return RestResultUtils.success(menuService.getMenus());
    }

    /**
     * 菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping("menu/get/{menuId}")
    @Secured(name = "模板菜单详情", resource = "templates:menu/get", action = ActionTypes.READ)
	public RestResult<Menu> getMenu(@PathVariable("menuId") Long menuId) {
        return RestResultUtils.success(menuService.getById(menuId));
    }

    /**
     * 保存菜单
     * @param menu
     * @return
     */
    @PostMapping("menu/save")
    @Secured(name = "模板菜单保存", resource = "templates:menu/save", action = ActionTypes.WRITE)
	public RestResult<Boolean> saveMenu(@Validated Menu menu) {
        return RestResultUtils.success(menuService.saveOrUpdate(menu));
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @PostMapping("menu/delete/{menuId}")
    @Secured(name = "模板菜单删除", resource = "templates:menu/delete", action = ActionTypes.WRITE)
	public RestResult<Boolean> doDeleteMenu(@PathVariable("menuId") Long menuId) {

        List<Menu> list = menuService.list(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, menuId));
        if(list != null && !list.isEmpty()) {
            return RestResultUtils.failed("请先删除该菜单的子菜单");
        }

        return RestResultUtils.success(menuService.removeById(menuId));
    }

    Path getFilePath(String filePath) {
        Template currTemplate = templateService.getCurrTemplate();
        return Paths.get(currTemplate.getTemplatePath().toString().concat(filePath.substring(currTemplate.getPathName().length())));
    }

}
