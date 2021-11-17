import request from '/@/utils/request';

/**
 * 分页获取用户数据
 * @param params 
 * @returns 
 */
export function getUserList(params?: object) {
	return request({
		url: '/admin/user/list',
		method: 'get',
		params,
	});
}

/**
 * 保存用户数据
 * @param params 
 * @returns 
 */
export function saveUser(params?: object) {
	return request({
		url: '/admin/user/save',
		method: 'post',
		params,
	});
}

/**
 * 删除用户数据
 * @param userId
 * @returns 
 */
export function delUser(userId?: string) {
	return request({
		url: '/admin/user/delete/' + userId,
		method: 'post'
	});
}
