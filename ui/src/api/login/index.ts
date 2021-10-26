import request from '/@/utils/request';

/**
 * 用户登录
 * @param params 要传的参数值
 * @returns 返回接口数据
 */
export function signIn(params: string) {
	return request({
		url: '/fastcms/login',
		method: 'post',
		data: params,
	});
}

/**
 * 用户退出登录
 * @param params 要传的参数值
 * @returns 返回接口数据
 */
export function signOut(params: object) {
	return request({
		url: '/user/signOut',
		method: 'post',
		data: params,
	});
}

/**
 * 获取验证码
 */
export function getCaptcha() {
	return request({
		url: '/fastcms/captcha',
		method: 'get'
	});
}
