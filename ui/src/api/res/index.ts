import request from '/@/utils/request';

/**
 * 获取资源数据
 * @param params 
 * @returns 
 */
export function getResList(params?: object) {
	return request({
		url: '/admin/resource/list',
		method: 'get',
		params,
	});
}

/**
 * 同步资源数据
 * @param params 
 * @returns 
 */
export function syncRes(params?: object) {
	return request({
		url: '/admin/resource/sync',
		method: 'post',
		params,
	});
}

