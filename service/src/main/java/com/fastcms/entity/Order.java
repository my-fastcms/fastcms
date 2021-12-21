package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author wjun_java@163.com
 * @since 2021-12-21
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 交易状态
     */
    public static final int TRADE_STATUS_TRADING = 1;               //交易中
    public static final int TRADE_STATUS_COMPLETED = 2;             //交易完成（但是可以申请退款）
    public static final int TRADE_STATUS_CANCEL = 3;                //取消交易
    public static final int TRADE_STATUS_APPLY_FOR_REFUNDING = 4;   //申请退款
    public static final int TRADE_STATUS_REFUSAL_REFUNDING = 5;     //拒绝退款
    public static final int TRADE_STATUS_REFUNDING = 6;             //退款中
    public static final int TRADE_STATUS_REFUNDED = 7;              //退款完成
    public static final int TRADE_STATUS_FINISHED = 8;              //交易结束
    public static final int TRADE_STATUS_CLOSED = 9;                //订单关闭

    /**
     * payment 状态
     */
    public static final int STATUS_PAY_PRE = 0;     //未支付
    public static final int STATUS_PAY_SUCCESS = 1; //支付成功
    public static final int STATUS_PAY_FAILURE = 2; //支付失败

    /**
     * 发货状态（物流状态）
     */
    public static final int DELIVERY_STATUS_UNDELIVERY = 1;         //未发货
    public static final int DELIVERY_STATUS_DELIVERIED = 2;         //已发货
    public static final int DELIVERY_STATUS_NEED_RE_DELIVERY = 3;   //需要补发（特殊情况下，物流出现问题或者其他争议需要重新发货）
    public static final int DELIVERY_STATUS_FINISHED = 4;           //用户已收货
    public static final int DELIVERY_STATUS_NONEED = 5;             //无需发货

    /**
     * 发布开具状态
     */
    public static final int INVOICE_STATUS_NOT_APPLY = 1;           //未申请发票
    public static final int INVOICE_STATUS_APPLYING = 2;            //发票申请中
    public static final int INVOICE_STATUS_INVOICING = 3;           //发票开具中
    public static final int INVOICE_STATUS_WITHOUT = 4;              //无需开具发票
    public static final int INVOICE_STATUS_INVOICED = 5;            //发票已经开具

    /**
     * 订单删除状态
     */
    public static final int ORDER_STATUS_NORMAL = 1;           //正常状态
    public static final int ORDER_STATUS_DEL = 0;              //已经删除

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 购买人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long userId;

    private String orderTitle;

    /**
     * 用户留言
     */
    private String buyerMsg;

    /**
     * 订单商品金额之和
     */
    private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 支付记录
     */
    private Long paymentId;

    /**
     * 发货情况
     */
    private Long deliveryId;

    /**
     * 1待发货，2已发货
     */
    private Integer deliveryStatus;

    /**
     * 收货人地址
     */
    private String consigneeUsername;

    /**
     * 收货人手机号（电话）
     */
    private String consigneeMobile;

    /**
     * 收件人的详细地址
     */
    private String consigneeAddrDetail;

    /**
     * 发票
     */
    private Integer invoiceId;

    /**
     * 发票开具状态：1 未申请发票、 2 发票申请中、 3 发票开具中、 4 无需开具发票、 5发票已经开具
     */
    private Integer invoiceStatus;

    /**
     * 订单邮费
     */
    private BigDecimal postageAmount;

    /**
     * 支付金额，商品金额 + 邮费 - 优惠或减免金额
     */
    private BigDecimal payAmount;

    /**
     * 管理员后台备注
     */
    private String remarks;

    /**
     * 交易状态：1交易中、 2交易完成（但是可以申请退款） 、3取消交易 、4申请退款、 5拒绝退款、 6退款中、 7退款完成、 8交易结束
     */
    private Integer tradeStatus;

    @Version
    private Integer version;

    /**
     * 删除状态：1 正常 ，0 已经删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime created;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;

    @TableField(exist = false)
    List<OrderItem> orderItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }
    public String getBuyerMsg() {
        return buyerMsg;
    }

    public void setBuyerMsg(String buyerMsg) {
        this.buyerMsg = buyerMsg;
    }
    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }
    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public Long getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(Long deliveryId) {
        this.deliveryId = deliveryId;
    }
    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    public String getConsigneeUsername() {
        return consigneeUsername;
    }

    public void setConsigneeUsername(String consigneeUsername) {
        this.consigneeUsername = consigneeUsername;
    }
    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }
    public String getConsigneeAddrDetail() {
        return consigneeAddrDetail;
    }

    public void setConsigneeAddrDetail(String consigneeAddrDetail) {
        this.consigneeAddrDetail = consigneeAddrDetail;
    }
    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }
    public Integer getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Integer invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }
    public BigDecimal getPostageAmount() {
        return postageAmount;
    }

    public void setPostageAmount(BigDecimal postageAmount) {
        this.postageAmount = postageAmount;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderSn=" + orderSn +
            ", userId=" + userId +
            ", orderTitle=" + orderTitle +
            ", buyerMsg=" + buyerMsg +
            ", orderAmount=" + orderAmount +
            ", payStatus=" + payStatus +
            ", paymentId=" + paymentId +
            ", deliveryId=" + deliveryId +
            ", deliveryStatus=" + deliveryStatus +
            ", consigneeUsername=" + consigneeUsername +
            ", consigneeMobile=" + consigneeMobile +
            ", consigneeAddrDetail=" + consigneeAddrDetail +
            ", invoiceId=" + invoiceId +
            ", invoiceStatus=" + invoiceStatus +
            ", postageAmount=" + postageAmount +
            ", payAmount=" + payAmount +
            ", remarks=" + remarks +
            ", tradeStatus=" + tradeStatus +
            ", version=" + version +
            ", status=" + status +
            ", created=" + created +
            ", updated=" + updated +
        "}";
    }
}
