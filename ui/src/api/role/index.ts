import request from '/@/utils/request';

/**
 * 管理后台获取角色数据
 * @param params 
 * @returns 
 */
export function getRoleList(params?: object) {
	return request({
		url: '/admin/role/list',
		method: 'get',
		params,
	});
}

/**
 * 管理后台保存角色数据
 * @param params 
 * @returns 
 */
export function saveRole(params?: object) {
	return request({
		url: '/admin/role/save',
		method: 'post',
		params,
	});
}

/**
 * 管理后台删除角色数据
 * @param roleId 
 * @returns 
 */
export function delRole(roleId?: string) {
	return request({
		url: '/admin/role/delete/'+roleId,
		method: 'post'
	});
}
