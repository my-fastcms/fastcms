import request from '/@/utils/request';

export function DeptApi() {
	return {
		/**
		 * 获取部门数据
		 * @param params 
		 * @returns 
		 */
		getDeptList(params?: object) {
			return request({
				url: '/admin/department/list',
				method: 'get',
				params,
			});
		},

		/**
		 * 保存部门数据
		 * @param params 
		 * @returns 
		 */
		saveDept(params?: object) {
			return request({
				url: '/admin/department/save',
				method: 'post',
				params,
			});
		},

		/**
		 * 获取部门数据
		 * @param deptId 
		 * @returns 
		 */
		getDept(deptId?: number) {
			return request({
				url: '/admin/department/get/'+deptId,
				method: 'get'
			});
		},

		/**
		 * 删除部门数据
		 * @param deptId 
		 * @returns 
		 */
		delDept(deptId?: number) {
			return request({
				url: '/admin/department/delete/'+deptId,
				method: 'post'
			});
		},

	};
}