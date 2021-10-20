package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.mapper.SinglePageCommentMapper;

/**
 * <p>
 * 页面评论表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface ISinglePageCommentService extends IService<SinglePageComment> {

	Page<SinglePageCommentMapper.PageCommentVo> pageSinglePageComment(Page pageParam, QueryWrapper queryWrapper);

}
