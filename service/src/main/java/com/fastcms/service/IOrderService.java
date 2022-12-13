package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Order;
import com.fastcms.entity.OrderItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    Page<OrderListVo> pageOrder(Page pageParam, QueryWrapper queryWrapper);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailVo getOrderDetail(Long orderId);

    /**
     * 获取订单统计数据
     * @return
     */
    OrderStatVo getOrderStatData();

    /**
     * 查询限制时间内的订单
     * @param time 单位分钟
     * @return
     */
    List<Order> getUnPayOrderByLimitTime(int time);

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

    class OrderListVo implements Serializable {

        /**
         * 订单id
         */
        Integer id;

        /**
         * 订单编号
         */
        String orderSn;

        /**
         * 商品名称
         */
        String title;

        /**
         * 订单状态
         */
        Integer status;

        /**
         * 订单状态
         */
        String statusStr;

        /**
         * 支付状态
         */
        Integer payStatus;

        /**
         * 支付状态显示值
         */
        String payStatusStr;

        /**
         * 买家账号
         */
        String userName;

        /**
         * 买家昵称
         */
        String nickName;

        /**
         * 商品数量
         */
        Integer productCount;

        /**
         * 商品金额
         */
        BigDecimal totalAmount;

        /**
         * 支付金额
         */
        BigDecimal payAmount;

        /**
         * 创建时间
         */
        LocalDate created;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getStatusStr() {
            return Order.OrderStatus.getValue(getStatus());
        }

        public void setStatusStr(String statusStr) {
            this.statusStr = statusStr;
        }

        public Integer getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(Integer payStatus) {
            this.payStatus = payStatus;
        }

        public String getPayStatusStr() {
            return Order.PayStatus.getValue(getPayStatus());
        }

        public void setPayStatusStr(String payStatusStr) {
            this.payStatusStr = payStatusStr;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public Integer getProductCount() {
            return productCount;
        }

        public void setProductCount(Integer productCount) {
            this.productCount = productCount;
        }

        public BigDecimal getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(BigDecimal totalAmount) {
            this.totalAmount = totalAmount;
        }

        public BigDecimal getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(BigDecimal payAmount) {
            this.payAmount = payAmount;
        }

        public LocalDate getCreated() {
            return created;
        }

        public void setCreated(LocalDate created) {
            this.created = created;
        }

    }

    /**
     * 订单详情
     */
    class OrderDetailVo extends Order implements Serializable {
        /**
         * 买家昵称
         */
        String nickName;

        /**
         * 买家账号
         */
        String userName;

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    /**
     * 订单项
     */
    class OrderItemVo extends OrderItem {
        /**
         * 商品名称
         */
        String productTitle;
        /**
         * 商品缩略图
         */
        String productThumbnail;

        public String getProductTitle() {
            return productTitle;
        }

        public void setProductTitle(String productTitle) {
            this.productTitle = productTitle;
        }

        public String getProductThumbnail() {
            return productThumbnail;
        }

        public void setProductThumbnail(String productThumbnail) {
            this.productThumbnail = productThumbnail;
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

    interface OrderI18n {
        String ORDER_VIEW_SELF = "fastcms.order.view.self";
        String ORDER_ALREADY_PAYED = "fastcms.order.already.payed";
        String ORDER_ALREADY_PAYED_SN = "fastcms.order.already.payed.sn";
        String ORDER_UN_PAYED = "fastcms.order.unpayed";
        String ORDER_NOT_EXIST = "fastcms.order.not.exist";
        String ORDER_PAY_AMOUNT_ERROR = "fastcms.order.payamount.error";
        String ORDER_TYPE_NOT_NULL = "fastcms.order.type.not.null";
        String ORDER_CLASS_TYPE_IS_NULL = "fastcms.order.class.type.is.null";
    }

}
