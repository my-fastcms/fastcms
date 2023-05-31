import request from '/@/utils/request';

export function OrderApi() {
	return {
		/**
		 * 订单分页列表
		 * @param params 
		 * @returns 
		 */
		getOrderList(params: object) {
			return request({
				url: '/admin/order/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 订单详情
		 * @param id 
		 * @returns 
		 */
		getOrderDetail(id: string) {
			return request({
				url: '/admin/order/detail/'+id,
				method: 'get'
			})
		},

		/**
		 * 删除订单
		 * @param id 
		 * @returns 
		 */
		delOrder(id: string) {
			return request({
				url: '/admin/order/delete/'+id,
				method: 'post'
			});
		},


		/**
		 * 支付记录列表
		 * @param params 
		 * @returns 
		 */
		getPaymentList(params: object) {
			return request({
				url: '/admin/order/payment/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 支付记录详情
		 * @param id 
		 * @returns 
		 */
		getPaymentDetail(id: string) {
			return request({
				url: '/admin/order/payment/detail/'+id,
				method: 'get'
			})
		},

		/**
		 * 提现记录列表
		 * @param params 
		 * @returns 
		 */
		getCashoutList(params: object) {
			return request({
				url: '/admin/order/cashout/list',
				method: 'get',
				params: params
			});
		},

		/**
		 * 获取提现详情
		 * @param params 
		 * @returns 
		 */
		getCashoutDetail(payoutId: string) {
			return request({
				url: '/admin/order/cashout/detail/' + payoutId,
				method: 'get'
			});
		},

		/**
		 * 确认提现
		 * @param id 
		 * @returns 
		 */
		confirmCashOut(id: string) {
			return request({
				url: '/admin/order/cashout/confirm/'+id,
				method: 'post'
			});
		},

		/**
		 * 拒绝提现
		 * @param id 
		 * @returns 
		 */
		refuseCashOut(id: string, feedback: string) {
			return request({
				url: '/admin/order/cashout/refuse/'+id,
				method: 'post',
				data: feedback,
				headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			});
		},
	};
}