import request from '/@/utils/request';

/**
 * 订单分页列表
 * @param params 
 * @returns 
 */
 export function getOrderList(params: object) {
	return request({
		url: '/client/order/list',
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
        url: '/client/order/detail/'+id,
        method: 'get'
    })
}

/**
 * PC扫码支付
 * @param id 
 * @returns 
 */
export function paymentOrder(id: string) {
	return request({
        url: '/client/pay/wxPay/NATIVE/getQrPay?orderId='+id,
        method: 'get'
    })
}

/**
 * 检查订单支付状态
 * @param id 
 * @returns 
 */
export function checkOrderPayStatus(id: string) {
	return request({
        url: 'client/order/status/check/'+id,
        method: 'get'
    })
}

