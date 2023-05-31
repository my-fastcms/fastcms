import request from '/@/utils/request';

export function PluginApi() {
	return {

		/**
		 * 获取系统已安装插件列表
		 * @returns 
		 */
		getPluginList(params: object) {
			return request({
				url: '/admin/plugin/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 安装插件
		 */
		installPlugin() {
			return request({
				url: '/admin/plugin/install',
				method: 'post'
			});
		},

		/**
		 * 卸载插件
		 */
		unInstallPlugin(pluginId: string) {
			return request({
				url: '/admin/plugin/unInstall/' + pluginId,
				method: 'post'
			});
		},

		/**
		 * 启用插件
		 */
		startPlugin(pluginId: string) {
			return request({
				url: '/admin/plugin/start/' + pluginId,
				method: 'post'
			});
		},

		/**
		 * 停用插件
		 */
		stopPlugin(pluginId: string) {
			return request({
				url: '/admin/plugin/stop/' + pluginId,
				method: 'post'
			});
		},

		/**
		 * 获取插件配置url
		 * @param params 
		 * @returns 
		 */
		getPluginConfigUrl(pluginId: string) {
			return request({
				url: '/admin/plugin/config/url/' + pluginId,
				method: 'get'
			});
		}
	};
}