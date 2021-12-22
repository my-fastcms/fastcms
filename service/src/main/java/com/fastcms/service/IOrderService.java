package com.fastcms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fastcms.entity.Order;
import com.fastcms.entity.PaymentRecord;

import java.io.Serializable;
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
    Page<OrderVo> pageOrder(Page pageParam, QueryWrapper queryWrapper);

    Long createOrder(CreateOrderParam createOrderParam) throws Exception;

    PaymentRecord preparePaymentRecord(Order order, String openid, String ip);

    OrderVo getOrderDetail(Long orderId);

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

    /**
     * 创建订单
     */
    class CreateOrderParam implements Serializable {
        /**
         * 用户id
         */
        Long userId;
        /**
         * 订单产品
         */
        List<ProductParam> products;
        /**
         * 收货地址
         */
        Long addressId;
        /**
         * 买家留言
         */
        String buyerMsg;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(Long userId) {
            this.userId = userId;
        }

        public List<ProductParam> getProducts() {
            return products;
        }

        public void setProducts(List<ProductParam> products) {
            this.products = products;
        }

        public Long getAddressId() {
            return addressId;
        }

        public void setAddressId(Long addressId) {
            this.addressId = addressId;
        }

        public String getBuyerMsg() {
            return buyerMsg;
        }

        public void setBuyerMsg(String buyerMsg) {
            this.buyerMsg = buyerMsg;
        }
    }

    /**
     * 产品
     */
    class ProductParam implements Serializable {
        /**
         * 产品id
         */
        Long id;
        /**
         * 产品类型
         */
        String type;
        /**
         * 产品数量
         */
        Long num;
        /**
         * 产品sku
         */
        String sku;

        public ProductParam(Long id, String type, Long num) {
            this.id = id;
            this.type = type;
            this.num = num;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Long getNum() {
            return num;
        }

        public void setNum(Long num) {
            this.num = num;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }
    }

}
