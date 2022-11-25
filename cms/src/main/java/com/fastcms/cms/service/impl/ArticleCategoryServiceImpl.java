package com.fastcms.cms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.cms.mapper.ArticleCategoryMapper;
import com.fastcms.cms.service.IArticleCategoryService;
import com.fastcms.common.model.TreeNode;
import com.fastcms.common.model.TreeNodeConvert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

/**
 * 文章分类服务实现类
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
@Service
public class ArticleCategoryServiceImpl<T extends TreeNode> extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements IArticleCategoryService, TreeNodeConvert<T> {

	@Override
	public List<CategoryTreeNode> getCategoryList() {
		List<ArticleCategory> articleCategoryList = list(Wrappers.<ArticleCategory>lambdaQuery().eq(ArticleCategory::getType, ArticleCategory.CATEGORY_TYPE));
		articleCategoryList.sort(Comparator.comparing(ArticleCategory::getSortNum));
		return (List<CategoryTreeNode>) getTreeNodeList(articleCategoryList);
	}

	@Override
	@Transactional
	public void deleteByCategoryId(Long articleCategoryId) {
		removeById(articleCategoryId);
		getBaseMapper().deleteRelationByCategoryId(articleCategoryId);
	}

	@Override
	public List<ArticleCategory> getArticleCategoryListByArticleId(Long articleId) {
		return getBaseMapper().getArticleCategoryListByArticleId(articleId, null);
	}

	@Override
	public T convert2Node(Object object) {
		ArticleCategory articleCategory = (ArticleCategory) object;
		return (T) new CategoryTreeNode(articleCategory);
	}

}
