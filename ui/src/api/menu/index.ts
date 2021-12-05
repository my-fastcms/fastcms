import request from '/@/utils/request';

/**
 * 获取后端动态路由菜单(根据用户权限加载菜单)
 * @param params 要传的参数值，非必传
 * @returns 返回接口数据
 */
export function getUserMenus(params?: object) {
	return request({
		url: '/admin/user/menus',
		method: 'get',
		params,
	});
}

/**
 * 获取全部菜单数据
 * @param params 
 * @returns 
 */
export function getMenuList(params?: object) {
	return request({
		url: '/admin/menu/list',
		method: 'get',
		params,
	});
}

/**
 * 保存菜单数据
 * @param params 
 * @returns 
 */
export function saveMenu(params?: object) {
	return request({
		url: '/admin/menu/save',
		method: 'post',
		params,
	});
}

/**
 * 删除菜单数据
 * @param menuId 
 * @returns 
 */
export function delMenu(menuId?: string) {
	return request({
		url: '/admin/menu/delete/'+menuId,
		method: 'post'
	});
}
