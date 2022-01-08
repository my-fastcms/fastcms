import request from '/@/utils/request';

/**
 * 文章列表
 * @param params 
 * @returns 
 */
export function getArticleList(params: object) {
	return request({
		url: '/client/article/list',
		method: 'get',
		params: params
	});
}

/**
 * 新建文章
 * @param params 
 * @returns 
 */
export function addArticle(params: string) {
	return request({
		url: '/client/article/save',
		method: 'post',
		data: params,
	});
}

/**
 * 获取文章详情
 * @param id 
 * @returns 
 */
export function getArticle(id: string) {
	return request({
		url: '/client/article/get/'+id,
		method: 'get'
	});
}

/**
 * 修改文章属性
 * @param id 
 * @param params 
 * @returns 
 */
export function updateArticle(id:string, params: object) {
	return request({
		url: '/client/article/update/'+id,
		method: 'post',
		params: params
	});
}

/**
 * 删除文章
 * @param id 
 * @returns 
 */
export function delArticle(id: string) {
	return request({
		url: '/client/article/delete/'+id,
		method: 'post'
	});
}

/**
 * 获取文章分类列表
 * @returns 
 */
 export function getArticleCategoryList() {
	return request({
		url: '/client/article/category/list',
		method: 'get'
	});
}

/**
 * 获取文章标签列表
 * @returns 
 */
 export function getArticleTagList() {
	return request({
		url: '/client/article/tag/list',
		method: 'get'
	});
}

/**
 * 获取文章评论列表
 * @returns 
 */
 export function getArticleCommentList(params: object) {
	return request({
		url: '/client/article/comment/list',
		method: 'get',
		params: params
	});
}

/**
 * 修改文章评论
 */
export function updateArticleComment(params: object) {
	return request({
		url: '/client/article/comment/save',
		method: 'post',
		params: params,
	});
}

/**
 * 删除文章评论
 */
 export function delArticleComment(commentId: string) {
	return request({
		url: '/client/article/comment/delete/' + commentId,
		method: 'post'
	});
}


