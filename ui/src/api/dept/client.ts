import request from '/@/utils/request';

/**
 * 获取部门数据(已启用)
 * @param params 
 * @returns 
 */
export function getDeptList(params?: object) {
	return request({
		url: '/client/department/list',
		method: 'get',
		params,
	});
}