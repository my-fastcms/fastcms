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
package com.fastcms.web.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.permission.AdminMenu;
import com.fastcms.core.utils.FileUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author： wjun_java@163.com
 * @date： 2021/2/19
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/attachment")
@AdminMenu(name = "附件", icon = "<i class=\"nav-icon fas fa-folder-open\"></i>", sort = 4)
public class AttachmentController {

    @Autowired
    private IAttachmentService attachmentService;

    @AdminMenu(name = "附件管理", sort = 1)
    @RequestMapping("list")
    public Object list(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                       @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize){
        LambdaQueryWrapper<Attachment> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.orderByDesc(Attachment::getCreated);
        Page pageParam = new Page<>(page, pageSize);
        Page<Attachment> pageData = attachmentService.page(pageParam, queryWrapper);
        return RestResultUtils.success(pageData);
    }

    @PostMapping("doUpload")
    @ExceptionHandler(value = MultipartException.class)
    public Object doUpload(@RequestParam("files") MultipartFile files[]) {

        List<String> errorFiles = new ArrayList<>();

        if(files != null && files.length>0) {

            for(MultipartFile file : files) {
                String newFilePath = FileUtils.newFileName(file.getOriginalFilename());
                File uploadFile = new File(FileUtils.getUploadDir(), newFilePath);
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
                    Attachment attachment = new Attachment();
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(newFilePath.replace("\\", "/"));
                    attachmentService.save(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(uploadFile != null) {
                        uploadFile.delete();
                    }
                    errorFiles.add(file.getOriginalFilename());
                }
            }

        }

        return errorFiles.isEmpty() ? RestResultUtils.success() : RestResultUtils.failed(errorFiles.stream().collect(Collectors.joining(",")).concat(",以上文件上传失败"));

    }

    @RequestMapping("detail")
    public Object detail(@RequestParam(name = "id") Long id) {

        Attachment attachment = attachmentService.getById(id);

        File attachmentFile = new File(FileUtils.getUploadDir(), attachment.getFilePath());

        long fileLen = attachmentFile.length();
        String fileLenUnit = "Byte";
        if (fileLen > 1024) {
            fileLen = fileLen / 1024;
            fileLenUnit = "KB";
        }
        if (fileLen > 1024) {
            fileLen = fileLen / 1024;
            fileLenUnit = "MB";
        }
        attachment.setFileSize(fileLen + fileLenUnit);

        return RestResultUtils.success(attachment);
    }

    @PostMapping("doDelete")
    public Object doDelete(@RequestParam(name = "id") Long id) {
        Attachment attachment = attachmentService.getById(id);
        if(attachment == null) return RestResultUtils.failed("文件不存在");

        if(attachmentService.removeById(attachment.getId())) {
            //删除文件
            File file = new File(FileUtils.getUploadDir() + attachment.getFilePath());
            if(file.exists() && file.isFile()) {
                file.delete();
            }
        }

        return RestResultUtils.success();
    }

    @RequestMapping("browse")
    public Object browse(@RequestParam(name = "page", required = false, defaultValue = "1") Long page,
                         @RequestParam(name = "pageSize", required = false, defaultValue = "10") Long pageSize) {
        LambdaQueryWrapper<Attachment> queryWrapper = new QueryWrapper().lambda();
        queryWrapper.orderByDesc(Attachment::getCreated);
        Page pageParam = new Page<>(page, pageSize);
        Page<Attachment> pageData = attachmentService.page(pageParam, queryWrapper);
        return RestResultUtils.success(pageData);
    }

    @PostMapping("doUploadOfCKEditor")
    @ExceptionHandler(value = MultipartException.class)
    public Object doUploadOfCKEditor(@RequestParam("upload") MultipartFile file) {
        return uploadOfCKEditor(file, false);
    }

    @PostMapping("doUploadOfCKEditorBrowse")
    @ExceptionHandler(value = MultipartException.class)
    public Object doUploadOfCKEditorBrowse(@RequestParam("file") MultipartFile file) {
        return uploadOfCKEditor(file, true);
    }

    Object uploadOfCKEditor(MultipartFile file, boolean isBrowse) {
        String newFilePath = FileUtils.newFileName(file.getOriginalFilename());
        File uploadFile = new File(FileUtils.getUploadDir(), newFilePath);
        try {
            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            file.transferTo(uploadFile);
            long fileSize = uploadFile.length();
            if(fileSize > 1024 * 1024 * 5) {
                uploadFile.delete();
                return RestResultUtils.failed("文件超过上传限制5MB");
            }
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFilePath(newFilePath.replace("\\", "/"));
            attachmentService.save(attachment);
            Map<String,Object> result = new HashMap<>();
            if(isBrowse){
                result.put("src", attachment.getFilePath());
            }else{
                result.put("fileName", attachment.getFileName());
                result.put("uploaded", 1);
                result.put("url", attachment.getPath());
            }
            return RestResultUtils.success(result);
        } catch (IOException e) {
            e.printStackTrace();
            if(uploadFile != null) {
                uploadFile.delete();
            }
            return RestResultUtils.failed(e.getMessage());
        }
    }

}
