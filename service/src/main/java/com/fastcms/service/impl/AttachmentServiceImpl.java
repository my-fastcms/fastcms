package com.fastcms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.entity.Attachment;
import com.fastcms.mapper.AttachmentMapper;
import com.fastcms.service.IAttachmentService;
import org.springframework.stereotype.Service;

/**
 * 附件服务实现类
 * @author wjun_java@163.com
 * @since 2021-02-19
 */
@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentMapper, Attachment> implements IAttachmentService {

}
