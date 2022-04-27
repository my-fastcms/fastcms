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
 * 获取文章评论列表
 * @returns 
 */
  export function getArticleCommentList(params: object) {
	return request({
		url: '/client/article/comment/user/list',
		method: 'get',
		params: params
	});
}
