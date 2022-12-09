package com.fastcms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Attachment;

/**
 *  附件服务类
 * @author wjun_java@163.com
 * @since 2021-02-19
 */
public interface IAttachmentService extends IService<Attachment> {

    interface AttachmentI18n {
        String ATTACHMENT_FILE_NOT_EXIST = "fastcms.attachment.not.exist";
    }

}
