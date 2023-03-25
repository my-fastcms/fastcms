import request from '/@/utils/request';

/**
 * 用户登录
 * @param params 要传的参数值
 * @returns 返回接口数据
 */
export function signIn(params: string) {
	return request({
		url: '/admin/login',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 用户退出登录
 * @param params 要传的参数值
 * @returns 返回接口数据
 */
export function signOut(params: object) {
	return request({
		url: '/admin/logout',
		method: 'post',
		data: params,
	});
}

/**
 * 获取验证码
 */
export function getCaptcha() {
	return request({
		url: '/admin/captcha',
		method: 'get'
	});
}

/**
 * 用户注册
 * @param params 
 * @returns 
 */
export function register(params :string) {
	return request({
		url: '/admin/register',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 重置密码
 * @param params 
 * @returns 
 */
 export function resetPassword(params :string) {
	return request({
		url: '/admin/reset/password',
		method: 'post',
		data: params,
		headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
	});
}

/**
 * 公众号扫码登录（需要安装公众号扫码登录插件https://www.xjd2020.com/a/c/plugins.html）
 * 获取登录二维码
 * @returns 
 */
 export function getLoginQrcode() {
	return request({
		baseURL: import.meta.env.VITE_PLUGIN_URL as any,
		url: '/wechat/mp/scan/qrcode/login/getLoginQrcode',
		method: 'get',
		headers: { 'Content-Type': 'application/json' },
	});
}

/**
 * 公众号扫码登录（需要安装公众号扫码登录插件https://www.xjd2020.com/a/c/plugins.html）
 * 获取二维码登录用户
 * @param sceneId 
 * @returns 
 */
export function getLoginUser(sceneId: string) {
	return request({
		baseURL: import.meta.env.VITE_PLUGIN_URL as any,
		url: '/wechat/mp/scan/qrcode/login/getLoginUser?sceneId=' + sceneId,
		method: 'get',
	});
}
