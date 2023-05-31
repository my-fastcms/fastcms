import request from '/@/utils/request';

export function ConfigApi() {
	return {
		/**
		 * 保存配置
		 * @param params 
		 * @returns 
		 */
		saveConfig(params: string) {
			return request({
				url: '/admin/config/save',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: params,
				method: 'post'
			});
		},

		/**
		 * 根据配置key值获取配置
		 * @param params 
		 * @returns 
		 */
		getConfigList(params: string) {
			return request({
				url: '/admin/config/list',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: params,
				method: 'post'
			});
		},

		/**
		 * 根据配置key值获取配置(无需登录)
		 * @param params 
		 * @returns 
		 */
		getPublicConfigList(params: string) {
			return request({
				url: '/admin/config/public/list',
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: params,
				method: 'post'
			});
		},

		/**
		 * 测试邮件配置
		 * @returns 
		 */
		testMailConfig() {
			return request({
				url: '/admin/config/mail/test',
				method: 'get'
			});
		},
	};
}