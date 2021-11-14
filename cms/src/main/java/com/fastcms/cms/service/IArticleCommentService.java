package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.common.constants.FastcmsConstants;

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

	class ArticleCommentVo implements Serializable {

		/**
		 * ID
		 */
		Long id;
		/**
		 * 文章标题
		 */
		String articleTitle;
		/**
		 * 作者
		 */
		String author;
		/**
		 * 作者头像
		 */
		String authorAvatar;
		/**
		 * 评论内容
		 */
		String content;
		/**
		 * 回复内容
		 */
		String parentComment;
		/**
		 * 创建者
		 */
		LocalDateTime created;
		/**
		 * 回复列表
		 */
		List<ArticleCommentVo> replyCommentList;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getArticleTitle() {
			return articleTitle;
		}

		public void setArticleTitle(String articleTitle) {
			this.articleTitle = articleTitle;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getAuthorAvatar() {
			return authorAvatar;
		}

		public void setAuthorAvatar(String authorAvatar) {
			this.authorAvatar = authorAvatar;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getParentComment() {
			return parentComment;
		}

		public void setParentComment(String parentComment) {
			this.parentComment = parentComment;
		}

		public LocalDateTime getCreated() {
			return created;
		}

		public void setCreated(LocalDateTime created) {
			this.created = created;
		}

		public List<ArticleCommentVo> getReplyCommentList() {
			return replyCommentList;
		}

		public void setReplyCommentList(List<ArticleCommentVo> replyCommentList) {
			this.replyCommentList = replyCommentList;
		}

		public String getAuthorAvatarUrl() {
			return FastcmsConstants.STATIC_RESOURCE_PATH + authorAvatar;
		}
	}


}
