import request from '/@/utils/request';

export function ClientArticleApi() {
	return {
		/**
		 * 文章列表
		 * @param params 
		 * @returns 
		 */
		getArticleList: (params: object) => {
			return request({
				url: '/client/article/list',
				method: 'get',
				params: params
			});
		},

		addArticle: (params: object) => {
			return request({
				url: '/client/article/save',
				method: 'post',
				data: params,
			});
		},

		/**
		 * 获取文章详情
		 * @param id 
		 * @returns 
		 */
		getArticle: (id: string) => {
			return request({
				url: '/client/article/get/'+id,
				method: 'get'
			});
		},

		/**
		 * 删除文章
		 * @param id 
		 * @returns 
		 */
		delArticle: (id: string) => {
			return request({
				url: '/client/article/delete/'+id,
				method: 'post'
			});
		},

		/**
		 * 文章评论列表
		 * @param params 
		 * @returns 
		 */
		getArticleCommentList: (params: object) => {
			return request({
				url: '/client/article/comment/user/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 获取文章分类列表
		 * @returns 
		 */
		getArticleCategoryList: () => {
			return request({
				url: '/client/article/category/list',
				method: 'get'
			});
		},

		/**
		 * 获取文章标签列表
		 * @returns 
		 */
		getArticleTagList: () => {
			return request({
				url: '/client/article/tag/list',
				method: 'get'
			});
		}

	}
}