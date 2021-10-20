package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.mapper.ArticleCategoryMapper;
import com.fastcms.cms.service.IArticleCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService {

	@Override
	public List<ArticleCategory> getCategoryList(Long userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("user_id", userId);
		queryWrapper.eq("type", ArticleCategory.CATEGORY_TYPE);
		return list(queryWrapper);
	}

	@Override
	public List<ArticleCategory> getTagList(Long userId) {
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("user_id", userId);
		queryWrapper.eq("type", ArticleCategory.TAG_TYPE);
		return list(queryWrapper);
	}

	@Override
	@Transactional
	public void deleteByCategoryId(Long articleCategoryId) {
		removeById(articleCategoryId);
		getBaseMapper().deleteRelationByCategoryId(articleCategoryId);
	}

	@Override
	public List<ArticleCategory> getArticleCategoryListByArticleId(Long articleId) {
		return getBaseMapper().getArticleCategoryListByArticleId(articleId, ArticleCategory.CATEGORY_TYPE);
	}

	@Override
	public List<ArticleCategory> getArticleTagListByArticleId(Long articleId) {
		return getBaseMapper().getArticleCategoryListByArticleId(articleId, ArticleCategory.TAG_TYPE);
	}

	@Override
	public Page<ArticleCategoryVo> pageArticleCategory(Page pageParam, QueryWrapper queryWrapper) {
		return getBaseMapper().pageArticleCategory(pageParam, queryWrapper);
	}


}
