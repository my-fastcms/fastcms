package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.common.constants.FastcmsConstants;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public interface IArticleCommentService extends IService<ArticleComment> {

	/**
	 * 分页获取所有文章评论列表
	 * @param pageParam
	 * @param queryWrapper
	 * @return
	 */
	Page<ArticleCommentVo> pageArticleComment(Page pageParam, QueryWrapper queryWrapper);

	/**
	 * 分页查询具体某篇文章的评论列表
	 * @param articleId
	 * @return
	 */
	Page<ArticleCommentVo> pageArticleCommentByArticleId(Page pageParam, Long articleId);

	/**
	 * 发布文章评论
	 * @param articleId
	 * @param commentId
	 * @param content
	 */
	void saveArticleComment(Long articleId, Long commentId, String content) throws Exception;

	@Data
	class ArticleCommentVo implements Serializable {
		Long id;
		String articleTitle;
		String author;
		String authorAvatar;
		String content;
		String parentComment;
		LocalDateTime created;

		List<ArticleCommentVo> replyCommentList;

		public String getAuthorAvatarUrl() {
			return FastcmsConstants.STATIC_RESOURCE_PATH + authorAvatar;
		}
	}


}
