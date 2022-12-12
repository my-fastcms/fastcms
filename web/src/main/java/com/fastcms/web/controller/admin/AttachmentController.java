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
import com.fastcms.common.auth.ActionTypes;
import com.fastcms.common.auth.Secured;
import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResult;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.common.utils.DirUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.mybatis.PageModel;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import com.fastcms.utils.I18nUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.fastcms.service.IAttachmentService.AttachmentI18n.ATTACHMENT_FILE_NOT_EXIST;
import static com.fastcms.service.IResourceService.ResourceI18n.*;

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
    @Secured(name = RESOURCE_NAME_ATTACHMENT_LIST, resource = "attachment:list", action = ActionTypes.READ)
	public RestResult<Page<Attachment>> list(PageModel page,
                                             @RequestParam(value = "fileType", required = false) String fileType,
                                             @RequestParam(value = "fileName", required = false) String fileName) {
        Page<Attachment> pageData = attachmentService.page(page.toPage(),
                Wrappers.<Attachment>lambdaQuery().eq(!AuthUtils.isAdmin(), Attachment::getCreateUserId, AuthUtils.getUserId())
                        .eq(StringUtils.isNotBlank(fileType), Attachment::getFileType, fileType)
                        .like(StringUtils.isNotBlank(fileName), Attachment::getFileName, fileName)
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
    @Secured(name = RESOURCE_NAME_ATTACHMENT_UPLOAD, resource = "attachment:upload", action = ActionTypes.WRITE)
	public Object upload(@RequestParam("files") MultipartFile files[]) {
        return AttachUtils.upload(files, attachmentService);
    }

    /**
     * 修改附件
     * @param attachId      附件id
     * @param fileName      附件名称
     * @param fileDesc      附件描述
     * @return
     */
    @PostMapping("update/{attachId}")
    @Secured(name = RESOURCE_NAME_ATTACHMENT_EDIT, resource = "attachment:update", action = ActionTypes.WRITE)
	public RestResult<Boolean> update(@PathVariable("attachId") Long attachId,
                                      @RequestParam("fileName") String fileName,
                                      @RequestParam(value = "fileDesc", required = false) String fileDesc) {
        Attachment attachment = attachmentService.getById(attachId);
        if(attachment == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(ATTACHMENT_FILE_NOT_EXIST));
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
    @Secured(name = RESOURCE_NAME_ATTACHMENT_INFO, resource = "attachment:get", action = ActionTypes.READ)
	public RestResult<Attachment> detail(@PathVariable(name = "attachId") Long attachId) {

        Attachment attachment = attachmentService.getById(attachId);
        if(attachment == null) {
            return RestResultUtils.failed(I18nUtils.getMessage(ATTACHMENT_FILE_NOT_EXIST));
        }

        File attachmentFile = new File(DirUtils.getUploadDir(), attachment.getFilePath());

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
    @Secured(name = RESOURCE_NAME_ATTACHMENT_DELETE, resource = "attachment:delete", action = ActionTypes.WRITE)
	public Object delete(@PathVariable(name = "attachId") Long attachId) {
        Attachment attachment = attachmentService.getById(attachId);
        if(attachment == null) return RestResultUtils.failed(I18nUtils.getMessage(ATTACHMENT_FILE_NOT_EXIST));
        return AttachUtils.deleteAttachment(attachment, attachmentService);
    }

}
