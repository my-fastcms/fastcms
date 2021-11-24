import request from '/@/utils/request';

/**
 * 文章列表
 * @param params 
 * @returns 
 */
export function getArticleList() {
	return request({
		url: '/admin/article/list',
		method: 'get'
	});
}

/**
 * 新建文章
 * @param params 
 * @returns 
 */
export function addArticle(params: object) {
	return request({
		url: '/admin/article/save',
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
		url: '/admin/article/get/'+id,
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
		url: '/admin/article/update/'+id,
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
		url: '/admin/article/delete/'+id,
		method: 'post'
	});
}

/**
 * 获取文章分类，包括标签列表，不分页
 * @returns 
 */
export function getArticleCategoryList() {
	return request({
		url: '/admin/article/category/list',
		method: 'get'
	});
}