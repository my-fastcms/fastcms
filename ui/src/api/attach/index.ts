import request from '/@/utils/request';

/**
 * 附件列表
 * @param params 
 * @returns 
 */
export function getAttachList() {
	return request({
		url: '/admin/attachment/list',
		method: 'get'
	});
}

/**
 * 上传附件
 * @param params 
 * @returns 
 */
export function addAttach(params: object) {
	return request({
		url: '/admin/attachment/upload',
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
		url: '/admin/attachment/get/'+id,
		method: 'post'
	});
}

/**
 * 删除文件
 * @param id 
 * @returns 
 */
export function delAttach(id: string) {
	return request({
		url: '/admin/attachment/delete/'+id,
		method: 'post'
	});
}
