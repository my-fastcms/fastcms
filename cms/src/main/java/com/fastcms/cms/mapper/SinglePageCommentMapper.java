package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePageComment;
import com.fastcms.cms.service.ISinglePageCommentService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 页面评论表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface SinglePageCommentMapper extends BaseMapper<SinglePageComment> {

	Page<ISinglePageCommentService.PageCommentVo> pageSinglePageComment(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

}
