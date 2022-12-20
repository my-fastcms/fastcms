import request from '/@/utils/request';

/**
 * 保存配置
 * @param params 
 * @returns 
 */
export function saveConfig(params: string) {
	return request({
		url: '/admin/config/save',
		data: params,
		method: 'post'
	});
}

/**
 * 根据配置key值获取配置
 * @param params 
 * @returns 
 */
export function getConfigList(params: string) {
	return request({
		url: '/admin/config/list',
		data: params,
		method: 'post'
	});
}

/**
 * 根据配置key值获取配置(无需登录)
 * @param params 
 * @returns 
 */
export function getPublicConfigList(params: string) {
	return request({
		url: '/admin/config/public/list',
		data: params,
		method: 'post'
	});
}

/**
 * 测试邮件配置
 * @returns 
 */
export function testMailConfig() {
	return request({
		url: '/admin/config/mail/test',
		method: 'get'
	});
}