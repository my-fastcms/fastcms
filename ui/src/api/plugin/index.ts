import request from '/@/utils/request';

/**
 * 获取系统已安装插件列表
 * @returns 
 */
export function getPluginList(params: object) {
	return request({
		url: '/admin/plugin/list',
		method: 'get',
		params: params
	});
}

/**
 * 安装插件
 */
export function installPlugin() {
	return request({
		url: '/admin/plugin/install',
		method: 'post'
	});
}

/**
 * 卸载插件
 */
export function unInstallPlugin(pluginId: string) {
	return request({
		url: '/admin/plugin/unInstall/' + pluginId,
		method: 'post'
	});
}

/**
 * 启用插件
 */
export function startPlugin(pluginId: string) {
	return request({
		url: '/admin/plugin/start/' + pluginId,
		method: 'post'
	});
}

/**
 * 停用插件
 */
 export function stopPlugin(pluginId: string) {
	return request({
		url: '/admin/plugin/stop/' + pluginId,
		method: 'post'
	});
}

/**
 * 获取插件配置url
 * @param params 
 * @returns 
 */
export function getPluginConfigUrl(pluginId: string) {
	return request({
		url: '/admin/plugin/config/url/' + pluginId,
		method: 'get'
	});
}