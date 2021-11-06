import request from '/@/utils/request';

/**
 * 后端控制菜单模拟json，路径在 https://gitee.com/lyt-top/vue-next-admin-images/tree/master/menu
 * 后端控制路由，isRequestRoutes 为 true，则开启后端控制路由
 */

/**
 * 获取后端动态路由菜单(根据用户权限加载菜单)
 * @link 参考：https://gitee.com/lyt-top/vue-next-admin-images/tree/master/menu
 * @param params 要传的参数值，非必传
 * @returns 返回接口数据
 */
export function getUserMenus(params?: object) {
	return request({
		url: '/admin/user/getMenus',
		method: 'get',
		params,
	});
}

/**
 * 管理后台获取全部菜单数据
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
 * 管理后台保存菜单数据
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
 * 管理后台删除菜单数据
 * @param params 
 * @returns 
 */
export function delMenu(params?: object) {
	return request({
		url: '/admin/menu/del',
		method: 'post',
		params,
	});
}
