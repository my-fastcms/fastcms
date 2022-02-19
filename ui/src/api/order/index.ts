import request from '/@/utils/request';

/**
 * 订单分页列表
 * @param params 
 * @returns 
 */
export function getOrderList(params: object) {
	return request({
		url: '/admin/order/list',
		method: 'get',
		params: params
	});
}

/**
 * 订单详情
 * @param id 
 * @returns 
 */
export function getOrderDetail(id: string) {
    return request({
        url: '/admin/order/detail/'+id,
        method: 'get'
    })
}

/**
 * 删除订单
 * @param id 
 * @returns 
 */
export function delOrder(id: string) {
	return request({
		url: '/admin/order/delete/'+id,
		method: 'post'
	});
}

