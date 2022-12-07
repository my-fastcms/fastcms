package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.Article;
import com.fastcms.cms.entity.ArticleComment;
import com.fastcms.cms.mapper.ArticleCommentMapper;
import com.fastcms.cms.service.IArticleCommentService;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.utils.ArticleUtils;
import com.fastcms.common.exception.FastcmsException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 文章评论服务实现类
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements IArticleCommentService {

	@Autowired
	private IArticleService articleService;

	@Override
	public Page<ArticleCommentVo> pageArticleComment(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageArticleComment(pageParam, queryWrapper);
	}

	@Override
	public Page<ArticleCommentVo> pageArticleCommentByArticleId(Page pageParam, Long articleId, Long userId) {
		return getBaseMapper().pageArticleCommentByArticleId(pageParam, articleId, userId);
	}

	@Override
	public void saveArticleComment(@NotNull Long articleId, @NotNull Long commentId, @NotNull String content) throws FastcmsException {

		if (!ArticleUtils.isEnableArticleComment()) {
			throw new FastcmsException(FastcmsException.SERVER_ERROR, "系统已关闭文章评论功能");
		}

		if(articleId == null) {
			throw new FastcmsException(FastcmsException.INVALID_PARAM, "文章id不能为空");
		}

		if(StringUtils.isBlank(content)) {
			throw new FastcmsException(FastcmsException.INVALID_PARAM, "评论内容不能为空");
		}

		Article article = articleService.getById(articleId);
		if(article == null) {
			throw new FastcmsException(FastcmsException.INVALID_PARAM, "文章不存在");
		}

		if(!article.getCommentEnable()) {
			throw new FastcmsException(FastcmsException.INVALID_PARAM, "文章不允许评论");
		}

		ArticleComment articleComment = new ArticleComment();
		articleComment.setArticleId(articleId);
		articleComment.setContent(content);
		articleComment.setParentId(commentId);

		if (ArticleUtils.isEnableArticleCommentAdminVerify()) {
			articleComment.setStatus(ArticleComment.STATUS_UNAUDITED);
		} else {
			articleComment.setStatus(ArticleComment.STATUS_NORMAL);
		}

		save(articleComment);
	}

}
