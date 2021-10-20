package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.SinglePageComment;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 页面评论表 Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface SinglePageCommentMapper extends BaseMapper<SinglePageComment> {

	Page<SinglePageCommentMapper.PageCommentVo> pageSinglePageComment(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	@Data
	class PageCommentVo implements Serializable {
		Long id;
		String pageTitle;
		String author;
		String content;
		String parentComment;
		LocalDateTime created;
	}

}
