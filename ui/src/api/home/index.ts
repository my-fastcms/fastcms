import request from '/@/utils/request';


export function IndexApi() {
	return {
		/**
		 * 首页数据集合
		 * @returns 
		 */
		getIndexData() {
			return request({
				url: '/admin/index/data',
				method: 'get'
			});
		}
	};
}

