import request from '/@/utils/request';

/**
 * 保存用户数据
 * @param params 
 * @returns 
 */
export function updateUser(params?: string) {
	return request({
		url: '/client/user/save',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 获取用户信息
 * @param params 
 * @returns 
 */
 export function getUserInfo() {
	return request({
		url: '/client/user/get',
		method: 'get',
	});
}