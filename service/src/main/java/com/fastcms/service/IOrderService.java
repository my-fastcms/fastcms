package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Order;
import com.fastcms.entity.PaymentRecord;

import java.io.Serializable;

/**
 * 订单服务类
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
public interface IOrderService extends IService<Order> {

    /**
     * 订单分页列表
     * @param pageParam
     * @param queryWrapper
     * @return
     */
    Page<OrderVo> pageOrder(Page pageParam, QueryWrapper queryWrapper);

    /**
     * @param order
     * @param openid
     * @param ip
     * @return
     */
    PaymentRecord preparePaymentRecord(Order order, String openid, String ip);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderVo getOrderDetail(Long orderId);

    /**
     * 获取订单统计数据
     * @return
     */
    OrderStatVo getOrderStatData();

    /**
     * 订单统计
     */
    class OrderStatVo implements Serializable {
        Long totalCount;
        Long todayCount;

        public Long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(Long totalCount) {
            this.totalCount = totalCount;
        }

        public Long getTodayCount() {
            return todayCount;
        }

        public void setTodayCount(Long todayCount) {
            this.todayCount = todayCount;
        }
    }

    class OrderVo extends Order {

        /**
         * 买家昵称
         */
        String nickName;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }

    class OrderCountVo implements Serializable {
        /**
         * 订单总数
         */
        Integer allCount;
        /**
         * 未支付数量
         */
        Integer unPay;
        /**
         * 已支付，待发货
         */
        Integer toSend;
        /**
         * 卖家已发货
         */
        Integer send;
        /**
         * 买家已确认收货(待评价)
         */
        Integer comment;
        /**
         * 已评价，交易成功
         */
        Integer success;
        /**
         * 订单关闭 订单超时未付款被关闭
         */
        Integer close;
        /**
         * 售后
         */
        Integer afterSale;

        public Integer getAllCount() {
            return allCount;
        }

        public void setAllCount(Integer allCount) {
            this.allCount = allCount;
        }

        public Integer getUnPay() {
            return unPay;
        }

        public void setUnPay(Integer unPay) {
            this.unPay = unPay;
        }

        public Integer getToSend() {
            return toSend;
        }

        public void setToSend(Integer toSend) {
            this.toSend = toSend;
        }

        public Integer getSend() {
            return send;
        }

        public void setSend(Integer send) {
            this.send = send;
        }

        public Integer getComment() {
            return comment;
        }

        public void setComment(Integer comment) {
            this.comment = comment;
        }

        public Integer getSuccess() {
            return success;
        }

        public void setSuccess(Integer success) {
            this.success = success;
        }

        public Integer getClose() {
            return close;
        }

        public void setClose(Integer close) {
            this.close = close;
        }

        public Integer getAfterSale() {
            return afterSale;
        }

        public void setAfterSale(Integer afterSale) {
            this.afterSale = afterSale;
        }
    }

}
