import request from '/@/utils/request';

/**
 * 首页数据集合
 * @returns 
 */
export function getIndexData() {
	return request({
		url: '/admin/index/data',
		method: 'get'
	});
}


