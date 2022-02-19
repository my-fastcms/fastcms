package com.fastcms.web.controller.api;

import com.fastcms.common.constants.FastcmsConstants;
import com.fastcms.common.model.RestResultUtils;
import com.fastcms.core.auth.AuthUtils;
import com.fastcms.core.utils.AttachUtils;
import com.fastcms.entity.Attachment;
import com.fastcms.service.IAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 附件
 * @author： wjun_java@163.com
 * @date： 2022/02/10
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@RestController
@RequestMapping(FastcmsConstants.API_MAPPING + "/attachment")
public class AttachmentApi {

	@Autowired
	private IAttachmentService attachmentService;

	/**
	 * 删除附件
	 * @param attachId    附件id
	 * @return
	 */
	@PostMapping("delete/{attachId}")
	public Object delete(@PathVariable(name = "attachId") Long attachId) {
		Attachment attachment = attachmentService.getById(attachId);
		if(attachment == null) return RestResultUtils.failed("文件不存在");

		if(attachment.getUserId() != AuthUtils.getUserId()) {
			return RestResultUtils.failed("只能删除自己的附件");
		}
		return AttachUtils.deleteAttachment(attachment, attachmentService);
	}

}
