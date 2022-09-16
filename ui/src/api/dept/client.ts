<<<<<<< HEAD
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
=======
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
>>>>>>> 9b22e8ee2077deb1d0ca28d39702bca483d28388
}