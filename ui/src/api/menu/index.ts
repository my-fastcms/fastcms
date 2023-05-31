import request from '/@/utils/request';

export function MenuApi() {

	return {
		/**
		 * 获取后端动态路由菜单(根据用户权限加载菜单)
		 * @param params 要传的参数值，非必传
		 * @returns 返回接口数据
		 */
		getUserMenus: (params: object) => {
			return request({
				url: '/admin/permission/menus',
				method: 'get',
				params,
			});
		},

		/**
		 * 获取全部菜单数据
		 * @param params 
		 * @returns 
		 */
		getMenuList: (params?: object) => {
			return request({
				url: '/admin/router/list',
				method: 'get',
				params,
			});
		},

		/**
		 * 保存菜单数据
		 * @param params 
		 * @returns 
		 */
		saveMenu: (params?: object) => {
			return request({
				url: '/admin/router/save',
				method: 'post',
				params,
			});
		},

		/**
		 * 获取菜单数据
		 * @param id 
		 * @returns 
		 */
		getMenu: (id: String) => {
			return request({
				url: '/admin/router/get?id=' + id,
				method: 'get',
			});
		},

		/**
		 * 删除菜单数据
		 * @param routerId 
		 * @returns 
		 */
		delMenu: (routerId?: string) => {
			return request({
				url: '/admin/router/delete/' + routerId,
				method: 'post'
			});
		}

	}

}