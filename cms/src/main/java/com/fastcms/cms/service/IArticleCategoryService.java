package com.fastcms.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.cms.entity.ArticleCategory;
import com.fastcms.common.model.TreeNode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章分类服务类
 * @author wjun_java@163.com
 * @since 2021-05-23
 */
public interface IArticleCategoryService extends IService<ArticleCategory> {

	/**
	 * 获取用户分类集合
	 * @param userId
	 * @return
	 */
	List<CategoryTreeNode> getCategoryList(Long userId);

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
	 * 分类vo
	 */
	class CategoryTreeNode extends TreeNode {

		/**
		 * 分类标题
		 */
		private String title;

		/**
		 * 访问标识
		 */
		private String suffix;

		/**
		 * 菜单图标
		 */
		private String icon;

		/**
		 * 创建时间
		 */
		private LocalDateTime created;

		public CategoryTreeNode(Long id, Long parentId, String title, String icon, String suffix, LocalDateTime created, int sortNum) {
			super(id, parentId, title);
			this.setSortNum(sortNum);
			this.title = title;
			this.icon = icon;
			this.suffix = suffix;
			this.created = created;
		}


		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public LocalDateTime getCreated() {
			return created;
		}

		public void setCreated(LocalDateTime created) {
			this.created = created;
		}
	}

}
