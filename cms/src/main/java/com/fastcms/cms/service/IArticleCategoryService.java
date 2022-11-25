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
	 * 获取分类列表
	 * @return
	 */
	List<CategoryTreeNode> getCategoryList();

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
		 * 模板标识
		 */
		private String suffix;

		/**
		 * 访问路径
		 */
		private String path;

		/**
		 * 菜单图标
		 */
		private String icon;

		/**
		 * 创建时间
		 */
		private LocalDateTime created;

		/**
		 * 分类url
		 */
		private String url;

		public CategoryTreeNode(ArticleCategory articleCategory) {
			super(articleCategory.getId(), articleCategory.getParentId(), articleCategory.getTitle());
			this.setSortNum(articleCategory.getSortNum());
			this.title = articleCategory.getTitle();
			this.icon = articleCategory.getIcon();
			this.suffix = articleCategory.getSuffix();
			this.path = articleCategory.getPath();
			this.created = articleCategory.getCreated();
			this.url = articleCategory.getUrl();
		}


		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
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

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

	}

}
