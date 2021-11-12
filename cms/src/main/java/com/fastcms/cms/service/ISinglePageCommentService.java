package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.SinglePageComment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 页面评论表 服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-25
 */
public interface ISinglePageCommentService extends IService<SinglePageComment> {

	Page<PageCommentVo> pageSinglePageComment(Page pageParam, QueryWrapper queryWrapper);

	class PageCommentVo implements Serializable {
		Long id;
		String pageTitle;
		String author;
		String content;
		String parentComment;
		LocalDateTime created;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPageTitle() {
			return pageTitle;
		}

		public void setPageTitle(String pageTitle) {
			this.pageTitle = pageTitle;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
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
	}

}
