package com.fastcms.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleCategory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public interface IArticleCategoryService extends IService<ArticleCategory> {

	/**
	 * 获取用户分类集合
	 * @param userId
	 * @return
	 */
	List<ArticleCategory> getCategoryList(Long userId);

	/**
	 * 获取用户标签激活
	 * @param userId
	 * @return
	 */
	List<ArticleCategory> getTagList(Long userId);

	/**
	 * 删除分类
	 * @param articleCategoryId
	 */
	void deleteByCategoryId(Long articleCategoryId);

	/**
	 * 获取文章已设置的分类
	 * @param articleId
	 * @return
	 */
	List<ArticleCategory> getArticleCategoryListByArticleId(Long articleId);

	/**
	 * 获取文章已设置的标签
	 * @param articleId
	 * @return
	 */
	List<ArticleCategory> getArticleTagListByArticleId(Long articleId);

	/**
	 * 分页查询文章分类列表
	 * @param pageParam
	 * @param queryWrapper
	 * @return
	 */
	Page<IArticleCategoryService.ArticleCategoryVo> pageArticleCategory(Page pageParam, QueryWrapper queryWrapper);

	class ArticleCategoryVo extends ArticleCategory {
		String createdUser;

		public String getCreatedUser() {
			return createdUser;
		}

		public void setCreatedUser(String createdUser) {
			this.createdUser = createdUser;
		}
	}

}
