package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.mapper.SinglePageCommentMapper;
import com.fastcms.cms.service.ISinglePageCommentService;
import org.springframework.stereotype.Service;

/**
 * 页面评论服务实现类
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
@Service
public class SinglePageCommentServiceImpl extends ServiceImpl<SinglePageCommentMapper, SinglePageComment> implements ISinglePageCommentService {

	@Override
	public Page<PageCommentVo> pageSinglePageComment(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageSinglePageComment(pageParam, queryWrapper);
	}
}
