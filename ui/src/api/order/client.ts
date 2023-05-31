import request from '/@/utils/request';


export function ClientOrderApi() {
    return {

        /**
         * 订单分页列表
         * @param params 
         * @returns 
         */
        getOrderList(params: object) {
            return request({
                url: '/client/order/list',
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
                url: '/client/order/detail/'+id,
                method: 'get'
            })
        },

        /**
         * PC扫码支付
         * @param id 
         * @returns 
         */
        paymentOrder(id: string) {
            return request({
                url: '/client/pay/wxPay/NATIVE/getQrPay?orderId='+id,
                method: 'get'
            })
        },

        /**
         * 检查订单支付状态
         * @param id 
         * @returns 
         */
        checkOrderPayStatus(id: string) {
            return request({
                url: '/client/order/status/check/'+id,
                method: 'get'
            })
        },

        /**
         * 获取用户账户资金信息
         * @param id 
         * @returns 
         */
        getUserAmount() {
            return request({
                url: '/client/user/amount/get',
                method: 'get'
            })
        },

        /**
         * 用户提现
         * @param amount
         * @returns 
         */
        cashoutAmount(amount: string) {
            return request({
                url: '/client/user/amount/cashout?amount='+amount,
                method: 'post'
            });
        },

        /**
         * 获取用户提现记录
         * @param id 
         * @returns 
         */
        getUserCashoutList() {
            return request({
                url: '/client/user/amount/cashout/list',
                method: 'get'
            })
        },

        /**
         * 用户收入记录
         * @returns 
         */
        getUserIncomeList() {
            return request({
                url: '/client/user/amount/income/list',
                method: 'get'
            })
        },
    };
}