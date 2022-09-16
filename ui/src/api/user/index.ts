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
export function saveUser(params?: string) {
	return request({
		url: '/admin/user/save',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
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

/**
 * 获取用户信息
 * @param params 
 * @returns 
 */
export function getUserInfo(userId?: string) {
	return request({
		url: '/admin/user/'+userId+'/get',
		method: 'get',
	});
}

/**
 * 获取标签列表
 * @returns 
 */
export function getTagList() {
	return request({
		url: '/admin/user/tag/list',
		method: 'get',
	});
}

/**
 * 修改密码
 * @param params 
 * @returns 
 */
export function updatePassword(params?: string) {
	return request({
		url: '/admin/user/password/update',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 重置用户密码
 * @param params 
 * @returns 
 */
 export function resetPassword(params?: string) {
	return request({
		url: '/admin/user/reset/password',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 改变用户类型
 * @param params 
 * @returns 
 */
export function changeUserType(params?: string) {
	return request({
		url: '/admin/user/changUserType',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}