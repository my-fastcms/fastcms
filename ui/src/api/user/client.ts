import request from '/@/utils/request';

export function ClientUserApi() {
	return {
		/**
		 * 保存用户数据
		 * @param params 
		 * @returns 
		 */
		updateUser(params?: object) {
			return request({
				url: '/client/user/save',
				method: 'post',
				data: params,
			});
		},

		/**
		 * 获取用户信息
		 * @param params 
		 * @returns 
		 */
		getUserInfo() {
			return request({
				url: '/client/user/get',
				method: 'get',
			});
		}
	};
}