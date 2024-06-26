import request from '/@/utils/request';

export function RoleApi() {
	return {
		/**
		 * 获取角色数据
		 * @param params 
		 * @returns 
		 */
		getRoleList(params?: object) {
			return request({
				url: '/admin/role/list',
				method: 'get',
				params,
			});
		},

		/**
		 * 保存角色数据
		 * @param params 
		 * @returns 
		 */
		saveRole(params?: object) {
			return request({
				url: '/admin/role/save',
				method: 'post',
				params,
			});
		},

		/**
		 * 删除角色数据
		 * @param roleId 
		 * @returns 
		 */
		delRole(roleId?: string) {
			return request({
				url: '/admin/role/delete/'+roleId,
				method: 'post'
			});
		},

		/**
		 * 保存角色权限数据
		 * @param params 
		 * @returns 
		 */
		saveRolePermissions(roleId:string, params?: object) {
			return request({
				url: '/admin/role/'+roleId+'/permissions/save',
				method: 'post',
				data: params,
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			});
		},

		/**
		 * 获取角色已分配路由权限
		 * @param roleId 
		 * @returns 
		 */
		getRolePermissions(roleId?: string) {
			return request({
				url: '/admin/role/'+roleId+'/permissions',
				method: 'get'
			});
		},

		/**
		 * 获取角色列表，不分页
		 * @returns 
		 */
		getRoleSelectList() {
			return request({
				url: '/admin/role/list/select',
				method: 'get'
			});
		},

	};
}