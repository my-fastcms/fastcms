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
package com.fastcms.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.Menu;
import com.fastcms.cms.service.IMenuService;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.response.Response;
import com.fastcms.core.template.Template;
import com.fastcms.core.template.TemplateService;
import com.fastcms.core.utils.FileUtils;
import com.fastcms.service.IConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/18
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Controller
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/template")
@AdminMenu(name = "模板", icon = "<i class=\"nav-icon fas fa-columns\"></i>", sort = 3)
public class TemplateController {

    private static final String FILES_ATTR = "files";
    private static final String DIRS_ATTR = "dirs";
    private static final String CURR_DIR_ATTR = "currDir";
    private static final String PARENT_DIR_ATTR = "parentDir";
    private static final String TEMPLATE_EDIT_VIEW = "admin/template/edit";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private IConfigService configService;

    @Autowired
    private IMenuService menuService;

    @AdminMenu(name = "模板管理", sort = 1)
    @RequestMapping("list")
    public String list(Model model) {
        model.addAttribute("templates", templateService.getTemplateList());
        model.addAttribute("enableTemplate", templateService.getCurrTemplate());
        return "admin/template/list";
    }

    @AdminMenu(name = "安装", sort = 3)
    @RequestMapping("install")
    public String install() {
        return "admin/template/install";
    }

    @PostMapping("doInstall")
    public ResponseEntity doInstall(@RequestParam("file") MultipartFile file) {

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf(".") + 1);
        //检查文件格式是否合法
        if(StringUtils.isEmpty(suffixName)) {
            return Response.fail("文件格式不合格，请上传zip文件");
        }
        if(!"zip".equalsIgnoreCase(suffixName)) {
            return Response.fail("文件格式不合格，请上传zip文件");
        }

        File uploadFile = new File(FileUtils.getTemplateDir(), file.getOriginalFilename());
        try {
            file.transferTo(uploadFile);
            templateService.install(uploadFile);
            return Response.success();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        } finally {
            if(uploadFile != null) {
                uploadFile.delete();
            }
        }
    }

    @PostMapping("doUnInstall")
    public ResponseEntity doUnInstall(@RequestParam("templateId") String templateId) {

        try {
            templateService.unInstall(templateId);
            return Response.success();
        } catch (Exception e) {
            return Response.fail(e.getMessage());
        }

    }

    @AdminMenu(name = "编辑", sort = 2)
    @RequestMapping("edit")
    public String edit(@RequestParam(name = "fileName", required = false) String fileName,
                       @RequestParam(name = "dirName", required = false) String dirName,
                       Model model) throws IOException {

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            model.addAttribute(FILES_ATTR, new ArrayList<>());
            return TEMPLATE_EDIT_VIEW;
        }

        model.addAttribute("templatePath", currTemplate.getPath());

        Path templatePath = currTemplate.getTemplatePath();

        if(StringUtils.isNotBlank(dirName)) {

            if(dirName.contains("..")) {
                dirName = "";
            }

            templatePath = Paths.get(templatePath.toString().concat("/" + dirName));

            String currDir = dirName.endsWith("/") ? dirName : dirName + "/";
            model.addAttribute(CURR_DIR_ATTR, currDir);
            int dotPos = StringUtils.lastOrdinalIndexOf(currDir, "/", 2);
            String parentDir;
            if(dotPos > 0) {
                parentDir = currDir.substring(0, dotPos + 1);
                model.addAttribute(PARENT_DIR_ATTR, parentDir);
            }
        }

        Stream<Path> list = Files.list(templatePath);
        List<Path> files = list.collect(Collectors.toList());
        List<Path> dirList = listPathDirs(files);
        List<Path> fileList = listPathFiles(files);

        StringBuilder stringBuilder = new StringBuilder();
        for (Path path : fileList) {
            stringBuilder.append(path.getFileName()).append(",");
        }
        model.addAttribute("strFiles", stringBuilder.toString());

        model.addAttribute(DIRS_ATTR, dirList);
        model.addAttribute(FILES_ATTR, fileList);

        if(StringUtils.isNotBlank(fileName) && fileName.contains("..")) {
            fileName = "index.html";
        }

        Path currFile = getCurrFile(fileList, fileName);

        if(currFile == null && !fileList.isEmpty()) {
            currFile = fileList.get(0);
        }

        if(currFile != null) {
            model.addAttribute("currFileName", currFile.getFileName());
            model.addAttribute("fileContent", FileUtils.escapeHtml(FileCopyUtils.copyToString(new FileReader(currFile.toFile()))));
        }

        return TEMPLATE_EDIT_VIEW;
    }

    @PostMapping("doEnable")
    public ResponseEntity doEnable(@RequestParam(name = "templateId") String templateId) {
        configService.saveConfig(FastcmsConstants.TEMPLATE_ENABLE_ID, templateId);
        return Response.success();
    }

    @PostMapping("doEditSave")
    public ResponseEntity doEditSave(String dirName, String fileName, String fileContent) throws IOException {
        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return Response.fail("没有找到模板");
        }
        Path templatePath = currTemplate.getTemplatePath();

        if(StringUtils.isNotBlank(dirName)) {
            if(dirName.contains("..")) dirName = "";
            templatePath = Paths.get(templatePath.toString().concat("/" + dirName));
        }

        if(StringUtils.isNotBlank(fileName) && fileName.contains("..")) {
            return Response.fail("没有找到模板");
        }

        Stream<Path> list = Files.list(templatePath);
        List<Path> files = listPathFiles(list.collect(Collectors.toList()));

        Path currFile = getCurrFile(files, fileName);

        if(currFile == null) {
            return Response.fail("文件不存在");
        }

        File file = currFile.toFile();
        if(!file.canWrite()) {
            return Response.fail("文件没有写入权限");
        }

        FileUtils.writeString(file, fileContent);

        return Response.success();
    }

    @PostMapping("doUpload")
    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity doUpload(String dirName, @RequestParam("files") MultipartFile files[]) {

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return Response.fail("没有找到模板");
        }
        Path templatePath = currTemplate.getTemplatePath();

        if(StringUtils.isNotBlank(dirName)) {
            if(dirName.contains("..")) {
                return Response.fail("目录不存在");
            }
            templatePath = Paths.get(templatePath.toString().concat("/" + dirName));
        }

        List<String> errorFiles = new ArrayList<>();

        if(files != null && files.length>0) {

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
                    long fileSize = uploadFile.length();
                    if(fileSize > 1024 * 1024 * 5) {
                        uploadFile.delete();
                        errorFiles.add(file.getOriginalFilename());
                        continue;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(uploadFile != null) {
                        uploadFile.delete();
                    }
                    errorFiles.add(file.getOriginalFilename());
                }
            }

        }

        return errorFiles.isEmpty() ? Response.success()
                : Response.fail(errorFiles.stream().collect(Collectors.joining(",")).concat(",以上文件上传失败"));
    }

    @PostMapping("doDelFile")
    public ResponseEntity doDelFile(String filePath) {

        if(StringUtils.isBlank(filePath)) {
            return Response.fail("文件路径为空");
        }

        if(filePath.contains("..")) {
            return Response.fail("文件路径不合法");
        }

        Template currTemplate = templateService.getCurrTemplate();
        if(currTemplate == null) {
            return Response.fail("没有找到模板");
        }
        Path templatePath = currTemplate.getTemplatePath();
        try {
            Paths.get(templatePath.toString() + File.separator + filePath).toFile().delete();
            return Response.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.fail(e.getMessage());
        }
    }

    @AdminMenu(name = "菜单", sort = 4)
    @RequestMapping("menu/list")
    public String menuList(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize,
                               Model model) {
        QueryWrapper queryWrapper = new QueryWrapper();
        Page pageParam = new Page<>(page, pageSize);
        Page<Menu> pageData = menuService.page(pageParam, queryWrapper);
//        model.addAttribute(PAGE_DATA_ATTR, pageData);
        return "admin/template/menu_list";
    }

    @RequestMapping("menu/edit")
    public String editMenu(@RequestParam(name = "id", required = false) Long id, Model model) {
        model.addAttribute("menu", menuService.getById(id));
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("status", "show");
        model.addAttribute("menus", menuService.list(queryWrapper));
        return "admin/template/menu_edit";
    }

    @PostMapping("menu/doSave")
    public ResponseEntity doSaveMenu(@Validated Menu menu) {
        menuService.saveOrUpdate(menu);
        return Response.success();
    }

    @PostMapping("menu/doDelete")
    public ResponseEntity doDeleteMenu(@RequestParam(name = "id") Long id) {
        menuService.removeById(id);
        return Response.success();
    }

    @RequestMapping("setting")
    @AdminMenu(name = "设置", sort = 5)
    public String setting() {
        return "admin/template/setting";
    }

    @PostMapping("setting/doSave")
    public ResponseEntity doSave(HttpServletRequest request) {
        return Response.success();
    }

    List<Path> listPathFiles(List<Path> files) {
        return files.stream().filter(item -> item.toFile().isFile() && !item.getFileName().toString().endsWith(".properties")).collect(Collectors.toList());
    }

    List<Path> listPathDirs(List<Path> files) {
        return files.stream().filter(item -> item.toFile().isDirectory()).collect(Collectors.toList());
    }

    Path getCurrFile(List<Path> files, String fileName) {
        for (Path file : files) {
            if(file.getFileName().toString().equals(fileName)){
                return file;
            }
        }
        return null;
    }

}
