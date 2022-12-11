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
import com.fastcms.common.exception.I18nFastcmsException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

import static com.fastcms.cms.service.IArticleService.ArticleI18n.*;

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
			throw new I18nFastcmsException(CMS_ARTICLE_COMMENT_DISABLE);
		}

		if(articleId == null) {
			throw new I18nFastcmsException(CMS_ARTICLE_COMMENT_ARTICLE_ID_NOT_ALLOW_NULL);
		}

		if(StringUtils.isBlank(content)) {
			throw new I18nFastcmsException(CMS_ARTICLE_COMMENT_CONTENT_IS_NOT_ALLOW_NULL);
		}

		Article article = articleService.getById(articleId);
		if(article == null) {
			throw new I18nFastcmsException(CMS_ARTICLE_IS_NOT_EXIST);
		}

		if(!article.getCommentEnable()) {
			throw new I18nFastcmsException(CMS_ARTICLE_COMMENT_IS_DISABLE);
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
