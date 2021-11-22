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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.core.utils.FileUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import org.apache.commons.lang3.StringUtils;
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
 * 附件管理
 * @author： wjun_java@163.com
 * @date： 2021/2/19
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.ADMIN_MAPPING + "/attachment")
public class AttachmentController {

    @Autowired
    private IAttachmentService attachmentService;

    /**
     * 附件列表
     * @param page
     * @param fileName  文件名称模糊搜索
     * @return
     */
    @RequestMapping("list")
    public RestResult<Page<Attachment>> list(PageModel page, @RequestParam(value = "fileName", required = false) String fileName) {
        Page<Attachment> pageData = attachmentService.page(page.toPage(), Wrappers.<Attachment>lambdaQuery().like(StringUtils.isNotBlank(fileName), Attachment::getFileName, fileName)
                .orderByDesc(Attachment::getCreated));
        return RestResultUtils.success(pageData);
    }

    /**
     * 上传附件
     * @param files     待上传文件
     * @return
     */
    @PostMapping("upload")
    @ExceptionHandler(value = MultipartException.class)
    public Object upload(@RequestParam("files") MultipartFile files[]) {

        List<String> errorFiles = new ArrayList<>();

        if(files != null && files.length>0) {
            List<Attachment> attachmentList = new ArrayList<>();
            for(MultipartFile file : files) {
                String newFilePath = FileUtils.newFileName(file.getOriginalFilename());
                File uploadFile = new File(FileUtils.getUploadDir(), newFilePath);
                try {
                    if (!uploadFile.getParentFile().exists()) {
                        uploadFile.getParentFile().mkdirs();
                    }
                    file.transferTo(uploadFile);
//                    long fileSize = uploadFile.length();
//                    if(fileSize > 1024 * 1024 * 5) {
//                        uploadFile.delete();
//                        errorFiles.add(file.getOriginalFilename());
//                        continue;
//                    }
                    Attachment attachment = new Attachment();
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFilePath(newFilePath.replace("\\", "/"));
                    attachmentList.add(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                    if(uploadFile != null) {
                        uploadFile.delete();
                    }
                    errorFiles.add(file.getOriginalFilename());
                }
            }

            if(!attachmentList.isEmpty()) {
                attachmentService.saveBatch(attachmentList);
            }

        }

        return errorFiles.isEmpty() ?
                RestResultUtils.success() :
                RestResultUtils.failed(errorFiles.stream().collect(Collectors.joining(",")).concat(",以上文件上传失败"));

    }

    /**
     * 修改附件名称以及描述
     * @param attachId      附件id
     * @param fileName      附件名称
     * @param fileDesc      附件描述
     * @return
     */
    @PostMapping("update/{attachId}")
    public RestResult<Boolean> update(@PathVariable("attachId") Long attachId,
                                      @RequestParam("fileName") String fileName,
                                      @RequestParam(value = "fileDesc", required = false) String fileDesc) {
        Attachment attachment = attachmentService.getById(attachId);
        if(attachment == null) {
            return RestResultUtils.failed("文件不存在");
        }

        attachment.setFileName(fileName);
        attachment.setFileDesc(fileDesc);

        return RestResultUtils.success(attachmentService.updateById(attachment));
    }

    /**
     * 附件明细
     * @param attachId    附件id
     * @return
     */
    @GetMapping("get/{attachId}")
    public RestResult<Attachment> detail(@PathVariable(name = "attachId") Long attachId) {

        Attachment attachment = attachmentService.getById(attachId);
        if(attachment == null) {
            return RestResultUtils.failed("附件不存在");
        }

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

    /**
     * 删除附件
     * @param attachId    附件id
     * @return
     */
    @PostMapping("delete/{attachId}")
    public Object delete(@PathVariable(name = "attachId") Long attachId) {
        Attachment attachment = attachmentService.getById(attachId);
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

    /**
     * 在编辑器中上传附件
     * @param file
     * @return
     */
    @PostMapping("upload/ckeditor")
    @ExceptionHandler(value = MultipartException.class)
    public Object upload4CKEditor(@RequestParam("upload") MultipartFile file) {
        return uploadOfCKEditor(file, false);
    }

    /**
     * 弹出框中上传附件
     * @param file
     * @return
     */
    @PostMapping("upload/cKeditor/browse")
    @ExceptionHandler(value = MultipartException.class)
    public Object upload4CKEditorBrowse(@RequestParam("file") MultipartFile file) {
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
