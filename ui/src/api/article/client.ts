import request from '/@/utils/request';

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
		url: '/client/article/comment/list',
		method: 'get',
		params: params
	});
}
