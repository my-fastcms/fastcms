import request from '/@/utils/request';

export function ResApi() {
	return {
		/**
		 * 获取资源数据
		 * @param params 
		 * @returns 
		 */
		getResList(params?: object) {
			return request({
				url: '/admin/resource/list',
				method: 'get',
				params,
			});
		},

		/**
		 * 同步资源数据
		 * @param params 
		 * @returns 
		 */
		syncRes(params?: object) {
			return request({
				url: '/admin/resource/sync',
				method: 'post',
				params,
			});
		}

	};
}