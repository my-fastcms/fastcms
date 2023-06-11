import request from '/@/utils/request';

export function ArticleApi() {
	return {
		getArticleList: (params: object) => {
			return request({
				url: '/admin/article/list',
				method: 'get',
				params: params
			});
		},
		
		addArticle: (params: object) => {
			return request({
				url: '/admin/article/save',
				method: 'post',
				data: params,
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			});
		},
		
		getArticle: (id: string) => {
			return request({
				url: '/admin/article/get/'+id,
				method: 'get'
			});
		},
		
		updateArticle: (id:string, params: object) => {
			return request({
				url: '/admin/article/update/'+ id,
				method: 'post',
				params: params
			});
		},

		delArticle: (id: string) => {
			return request({
				url: '/admin/article/delete/'+id,
				method: 'post'
			});
		},

		/**
		 * 新增文章分类
		 */
		addArticleCategory: (params: object) => {
			return request({
				url: '/admin/article/category/save',
				method: 'post',
				params: params,
			});
		},

		/**
		 * 获取文章分类列表
		 * @returns 
		 */
		getArticleCategoryList: () => {
			return request({
				url: '/admin/article/category/list',
				method: 'get'
			});
		},

		/**
		 * 获取文章分类
		 */
		getArticleCategory: (categoryId: string) => {
			return request({
				url: '/admin/article/category/get/' + categoryId,
				method: 'get'
			});
		},

		/**
		 * 删除文章分类
		 */
		delArticleCategory: (categoryId: string) => {
			return request({
				url: '/admin/article/category/delete/' + categoryId,
				method: 'post'
			});
		},

		/**
		 * 新增文章标签
		 */
		addArticleTag: (params: object) => {
			return request({
				url: '/admin/article/tag/save',
				method: 'post',
				params: params,
			});
		},

		/**
		 * 获取文章标签列表
		 * @returns 
		 */
		getArticleTagList() {
			return request({
				url: '/admin/article/tag/list',
				method: 'get'
			});
		},

		/**
		 * 删除文章标签
		 */
		getArticleTag(tagId: string) {
			return request({
				url: '/admin/article/tag/get/' + tagId,
				method: 'get'
			});
		},

		/**
		 * 删除文章标签
		 */
		delArticleTag(tagId: string) {
			return request({
				url: '/admin/article/tag/delete/' + tagId,
				method: 'post'
			});
		},

		/**
		 * 获取文章评论列表
		 * @returns 
		 */
		getArticleCommentList(params: object) {
			return request({
				url: '/admin/article/comment/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 修改文章评论
		 */
		updateArticleComment(params: object) {
			return request({
				url: '/admin/article/comment/save',
				method: 'post',
				params: params,
			});
		},

		/**
		 * 删除文章评论
		 */
		delArticleComment(commentId: string) {
			return request({
				url: '/admin/article/comment/delete/' + commentId,
				method: 'post'
			});
		},

	}
}
