package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.ArticleZan;
import com.fastcms.cms.mapper.ArticleZanMapper;
import com.fastcms.cms.service.IArticleService;
import com.fastcms.cms.service.IArticleZanService;
import com.fastcms.common.exception.FastcmsException;
import com.fastcms.common.exception.I18nFastcmsException;
import com.fastcms.utils.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2023-05-01
 */
@Service
public class ArticleZanServiceImpl extends ServiceImpl<ArticleZanMapper, ArticleZan> implements IArticleZanService {

	@Override
	public Page<ArticleZanVo> pageArticleZan(Page pageParam, Long articleId) {
		return getBaseMapper().pageArticleZan(pageParam, articleId);
	}

	@Override
	public ArticleZan saveZan(Long userId, Long articleId) throws FastcmsException {
		if (isUserHadZan(userId, articleId)) {
			throw new I18nFastcmsException(IArticleService.ArticleI18n.CMS_ARTICLE_USER_HAD_ZAN);
		}
		ArticleZan zan = new ArticleZan();
		zan.setArticleId(articleId);
		zan.setCreateUserId(userId);
		save(zan);
		return zan;
	}

	@Override
	public boolean cancelZan(Long userId, Long articleId) throws FastcmsException {
		LambdaQueryWrapper<ArticleZan> queryWrapper = Wrappers.<ArticleZan>lambdaQuery().eq(ArticleZan::getArticleId, articleId).eq(ArticleZan::getCreateUserId, userId);
		return remove(queryWrapper);
	}

	@Override
	public boolean isUserHadZan(Long userId, Long articleId) {
		LambdaQueryWrapper<ArticleZan> queryWrapper = Wrappers.<ArticleZan>lambdaQuery().eq(ArticleZan::getArticleId, articleId).eq(ArticleZan::getCreateUserId, userId);
		List<ArticleZan> list = list(queryWrapper);
		return CollectionUtils.isNotEmpty(list);
	}

}
