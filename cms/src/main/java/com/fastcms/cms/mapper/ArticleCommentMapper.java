package com.fastcms.cms.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.cms.service.IArticleCommentService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {

	Page<IArticleCommentService.ArticleCommentVo> pageArticleComment(Page pageParam, @Param(Constants.WRAPPER) QueryWrapper queryWrapper);

	List<IArticleCommentService.ArticleCommentVo> getReplyCommentByParentId(@Param("commentId") Long commentId);

	Page<IArticleCommentService.ArticleCommentVo> pageArticleCommentByArticleId(Page pageParam, @Param("articleId") Long articleId, @Param("userId") Long userId);

}
