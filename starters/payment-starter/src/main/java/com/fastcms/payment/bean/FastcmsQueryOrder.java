package com.fastcms.payment.bean;

import com.egzosn.pay.common.bean.AssistOrder;
import com.egzosn.pay.common.bean.BillType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class FastcmsQueryOrder extends AssistOrder implements Serializable {

    /**
     *     支付平台
     */
    private String platform;

    /**
     *     退款金额
     */
    private BigDecimal refundAmount;
    /**
     *     总金额
     */
    private BigDecimal totalAmount;
    /**
     *     账单时间：具体请查看对应支付平台
     */
    private Date billDate;
    /**
     *  账单类型：具体请查看对应支付平台
     *  详情实现查看各个平台组件对应实现类
     */
    private BillType billType;
    /**
     *     支付平台订单号或者账单日期
     */
    private Object tradeNoOrBillDate;
    /**
     * 商户单号或者 账单类型
     */
    private String outTradeNoBillType;
    /**
     * 交易类型，交易方式，
     * 本字段与{@link com.egzosn.pay.common.bean.PayOrder#getTransactionType}相同。
     *
     *  例如，网页支付，扫码付等等
     */
    private String wayTrade;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public BillType getBillType() {
        return billType;
    }

    public void setBillType(BillType billType) {
        this.billType = billType;
    }

    public Object getTradeNoOrBillDate() {
        return tradeNoOrBillDate;
    }

    public void setTradeNoOrBillDate(Object tradeNoOrBillDate) {
        this.tradeNoOrBillDate = tradeNoOrBillDate;
    }

    public String getOutTradeNoBillType() {
        return outTradeNoBillType;
    }

    public void setOutTradeNoBillType(String outTradeNoBillType) {
        this.outTradeNoBillType = outTradeNoBillType;
    }

    public String getWayTrade() {
        return wayTrade;
    }

    public void setWayTrade(String wayTrade) {
        this.wayTrade = wayTrade;
    }

}
