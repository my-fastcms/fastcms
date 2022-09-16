import request from '/@/utils/request';

/**
 * 获取部门数据
 * @param params 
 * @returns 
 */
export function getDeptList(params?: object) {
	return request({
		url: '/admin/department/list',
		method: 'get',
		params,
	});
}

/**
 * 保存部门数据
 * @param params 
 * @returns 
 */
export function saveDept(params?: object) {
	return request({
		url: '/admin/department/save',
		method: 'post',
		params,
	});
}

/**
 * 删除部门数据
 * @param deptId 
 * @returns 
 */
export function delDept(deptId?: string) {
	return request({
		url: '/admin/department/delete/'+deptId,
		method: 'post'
	});
}