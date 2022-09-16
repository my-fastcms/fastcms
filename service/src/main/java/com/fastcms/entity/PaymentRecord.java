package com.fastcms.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录表
 * @author wjun_java@163.com
 * @since 2021-11-12
 */
public class PaymentRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务类型
     */
    public static final String TRX_TYPE_RECHARGE = "recharge";      //用户充值
    public static final String TRX_TYPE_ORDER = "order";            //订单支付
    public static final String TRX_TYPE_MEMBER = "member";          //购买会员

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 相关产品ID
     */
    private Long productRelativeId;

    /**
     * 支付流水号
     */
    private String trxNo;

    /**
     * 交易业务类型  ：消费、充值等
     */
    private String trxType;

    /**
     * 签名随机字符串，一般是用来防止重放攻击
     */
    private String trxNonceStr;

    /**
     * 付款人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 付款方手续费
     */
    private BigDecimal payerFee;

    /**
     * 下单ip(客户端ip,从网关中获取)
     */
    private String orderIp;

    /**
     * 订单来源
     */
    private String orderFrom;

    /**
     * 支付状态：0生成订单未支付（预支付）、1支付成功、 2支付失败
     */
    private Integer payStatus;

    /**
     * 支付类型编号
     */
    private String payType;

    /**
     * 支付银行类型
     */
    private String payBankType;

    /**
     * 订单金额
     */
    private BigDecimal payAmount;

    /**
     * 成功支付金额
     */
    private BigDecimal paySuccessAmount;

    /**
     * 支付成功时间
     */
    private LocalDateTime paySuccessTime;

    /**
     * 第三方支付平台
     */
    private String thirdpartyType;

    /**
     * 微信appid 或者 支付宝的appid，thirdparty 指的是支付的第三方比如微信、支付宝、PayPal等
     */
    private String thirdpartyAppid;

    /**
     * 商户号
     */
    private String thirdpartyMchId;

    /**
     * 交易类型
     */
    private String thirdpartyTradeType;

    private String thirdpartyTransactionId;

    private String thirdpartyUserOpenid;

    /**
     * 备注
     */
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime updated;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getProductRelativeId() {
        return productRelativeId;
    }

    public void setProductRelativeId(Long productRelativeId) {
        this.productRelativeId = productRelativeId;
    }
    public String getTrxNo() {
        return trxNo;
    }

    public void setTrxNo(String trxNo) {
        this.trxNo = trxNo;
    }
    public String getTrxType() {
        return trxType;
    }

    public void setTrxType(String trxType) {
        this.trxType = trxType;
    }
    public String getTrxNonceStr() {
        return trxNonceStr;
    }

    public void setTrxNonceStr(String trxNonceStr) {
        this.trxNonceStr = trxNonceStr;
    }
    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
    public BigDecimal getPayerFee() {
        return payerFee;
    }

    public void setPayerFee(BigDecimal payerFee) {
        this.payerFee = payerFee;
    }
    public String getOrderIp() {
        return orderIp;
    }

    public void setOrderIp(String orderIp) {
        this.orderIp = orderIp;
    }
    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }
    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
    public String getPayBankType() {
        return payBankType;
    }

    public void setPayBankType(String payBankType) {
        this.payBankType = payBankType;
    }
    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }
    public BigDecimal getPaySuccessAmount() {
        return paySuccessAmount;
    }

    public void setPaySuccessAmount(BigDecimal paySuccessAmount) {
        this.paySuccessAmount = paySuccessAmount;
    }
    public LocalDateTime getPaySuccessTime() {
        return paySuccessTime;
    }

    public void setPaySuccessTime(LocalDateTime paySuccessTime) {
        this.paySuccessTime = paySuccessTime;
    }
    public String getThirdpartyType() {
        return thirdpartyType;
    }

    public void setThirdpartyType(String thirdpartyType) {
        this.thirdpartyType = thirdpartyType;
    }
    public String getThirdpartyAppid() {
        return thirdpartyAppid;
    }

    public void setThirdpartyAppid(String thirdpartyAppid) {
        this.thirdpartyAppid = thirdpartyAppid;
    }
    public String getThirdpartyMchId() {
        return thirdpartyMchId;
    }

    public void setThirdpartyMchId(String thirdpartyMchId) {
        this.thirdpartyMchId = thirdpartyMchId;
    }
    public String getThirdpartyTradeType() {
        return thirdpartyTradeType;
    }

    public void setThirdpartyTradeType(String thirdpartyTradeType) {
        this.thirdpartyTradeType = thirdpartyTradeType;
    }
    public String getThirdpartyTransactionId() {
        return thirdpartyTransactionId;
    }

    public void setThirdpartyTransactionId(String thirdpartyTransactionId) {
        this.thirdpartyTransactionId = thirdpartyTransactionId;
    }
    public String getThirdpartyUserOpenid() {
        return thirdpartyUserOpenid;
    }

    public void setThirdpartyUserOpenid(String thirdpartyUserOpenid) {
        this.thirdpartyUserOpenid = thirdpartyUserOpenid;
    }
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "PaymentRecord{" +
            "id=" + id +
            ", productRelativeId=" + productRelativeId +
            ", trxNo=" + trxNo +
            ", trxType=" + trxType +
            ", trxNonceStr=" + trxNonceStr +
            ", createUserId=" + createUserId +
            ", payerFee=" + payerFee +
            ", orderIp=" + orderIp +
            ", orderFrom=" + orderFrom +
            ", payStatus=" + payStatus +
            ", payType=" + payType +
            ", payBankType=" + payBankType +
            ", payAmount=" + payAmount +
            ", paySuccessAmount=" + paySuccessAmount +
            ", paySuccessTime=" + paySuccessTime +
            ", thirdpartyType=" + thirdpartyType +
            ", thirdpartyAppid=" + thirdpartyAppid +
            ", thirdpartyMchId=" + thirdpartyMchId +
            ", thirdpartyTradeType=" + thirdpartyTradeType +
            ", thirdpartyTransactionId=" + thirdpartyTransactionId +
            ", thirdpartyUserOpenid=" + thirdpartyUserOpenid +
            ", remark=" + remark +
            ", updated=" + updated +
            ", created=" + created +
        "}";
    }
}
