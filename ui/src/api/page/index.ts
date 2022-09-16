import request from '/@/utils/request';

/**
 * 单页列表
 * @param params 
 * @returns 
 */
export function getPageList(params: object) {
	return request({
		url: '/admin/page/list',
		method: 'get',
		params: params
	});
}

/**
 * 新建页面
 * @param params 
 * @returns 
 */
export function addPage(params: string) {
	return request({
		url: '/admin/page/save',
		method: 'post',
		data: params,
	});
}

/**
 * 获取单页详情
 * @param id 
 * @returns 
 */
export function getPage(id: string) {
	return request({
		url: '/admin/page/get/'+id,
		method: 'get'
	});
}

/**
 * 修改单页属性
 * @param id 
 * @param params 
 * @returns 
 */
export function updatePage(id:string, params: object) {
	return request({
		url: '/admin/page/update/'+id,
		method: 'post',
		params: params
	});
}

/**
 * 删除单页
 * @param id 
 * @returns 
 */
export function delPage(id: string) {
	return request({
		url: '/admin/page/delete/'+id,
		method: 'post'
	});
}

/**
 * 获取页面评论列表
 * @returns 
 */
 export function getPageCommentList(params: object) {
	return request({
		url: '/admin/page/comment/list',
		method: 'get',
		params: params
	});
}

/**
 * 修改页面评论
 */
export function updatePageComment(params: object) {
	return request({
		url: '/admin/page/comment/save',
		method: 'post',
		params: params,
	});
}

/**
 * 删除页面评论
 */
 export function delPageComment(commentId: string) {
	return request({
		url: '/admin/page/comment/delete/' + commentId,
		method: 'post'
	});
}