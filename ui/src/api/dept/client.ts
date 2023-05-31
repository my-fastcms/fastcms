import request from '/@/utils/request';

export function ClientDeptApi() {
	return {
		/**
		 * 获取部门数据(已启用)
		 * @param params 
		 * @returns 
		 */
		getDeptList(params?: object) {
			return request({
				url: '/client/department/list',
				method: 'get',
				params,
			});
		}
	};
}