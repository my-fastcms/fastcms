import request from '/@/utils/request';

/**
 * 附件列表
 * @param params 
 * @returns 
 */
 export function getAttachList(params: object) {
	return request({
		url: '/client/attachment/list',
		method: 'get',
		params: params
	});
}

/**
 * 上传附件
 * @param params 
 * @returns 
 */
export function addAttach(params: object) {
	return request({
		url: '/client/attachment/upload',
		method: 'post',
		data: params,
	});
}

/**
 * 获取附件详情
 * @param id 
 * @returns 
 */
export function getAttach(id: string) {
	return request({
		url: '/client/attachment/get/'+id,
		method: 'get'
	});
}

/**
 * 修改附件名称以及描述
 * @param id 
 * @param params 
 * @returns 
 */
export function updateAttach(id:string, params: object) {
	return request({
		url: '/client/attachment/update/'+id,
		method: 'post',
		params: params
	});
}

/**
 * 删除附件
 * @param id 
 * @returns 
 */
export function delAttach(id: string) {
	return request({
		url: '/client/attachment/delete/'+id,
		method: 'post'
	});
}
