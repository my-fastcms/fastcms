package com.dbumama.market.service.api.order;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractResultDto;

/**
 * 拼团成员信息
 * @author drs
 *
 */
@SuppressWarnings("serial")
public class OrderTuanGuserInfoDto extends AbstractResultDto{
	protected Long id;                         //拼团成员Id
	protected String headimg;                  //头像
	protected String receiverName;
	protected String receiverPhone;
	protected String receiverProvince;			//收件人所在省
	protected String receiverCity;				//收件人所在市
	protected String receiverCountry;			//收件人所在县区
	protected String receiverAddr;				//收件人详细地址
	protected String zipCode;					//收件人邮编
	protected String buyerNick;					//买家昵称
	protected String buyerMemo;					//买家留言
	protected String buyerMemoSimple;			//买家留言 缩减
	protected Integer num;						//订单项中 商品总数
	protected BigDecimal totalPrice;			//商品总价  订单项中商品单价 * 商品数量 的价格和
	protected BigDecimal oldPrice;				//原商品总价， 打折前，满减前的商品总价格
	protected BigDecimal oldPostFee;			//原邮费 满减前的邮费
	protected BigDecimal postFee;				//运费
	protected BigDecimal payFee;				//应付金额
	protected String transactionId;             //微信退款标识
	protected String paymentStatus;			//支付状态，拼团订单有效
	protected Long buyer_id;                //卖家Id
	
	public OrderTuanGuserInfoDto(){
		setReceiverCountry("");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHeadimg() {
		return headimg;
	}
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverProvince() {
		return receiverProvince;
	}
	public void setReceiverProvince(String receiverProvince) {
		this.receiverProvince = receiverProvince;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverCountry() {
		return receiverCountry;
	}
	public void setReceiverCountry(String receiverCountry) {
		this.receiverCountry = receiverCountry;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public void setReceiverAddr(String receiverAddr) {
		this.receiverAddr = receiverAddr;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getBuyerNick() {
		return buyerNick;
	}
	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}
	public String getBuyerMemo() {
		return buyerMemo;
	}
	public void setBuyerMemo(String buyerMemo) {
		this.buyerMemo = buyerMemo;
	}
	public String getBuyerMemoSimple() {
		return buyerMemoSimple;
	}
	public void setBuyerMemoSimple(String buyerMemoSimple) {
		this.buyerMemoSimple = buyerMemoSimple;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public BigDecimal getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(BigDecimal oldPrice) {
		this.oldPrice = oldPrice;
	}
	public BigDecimal getOldPostFee() {
		return oldPostFee;
	}
	public void setOldPostFee(BigDecimal oldPostFee) {
		this.oldPostFee = oldPostFee;
	}
	public BigDecimal getPostFee() {
		return postFee;
	}
	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}
	public BigDecimal getPayFee() {
		return payFee;
	}
	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Long getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(Long buyer_id) {
		this.buyer_id = buyer_id;
	}
	
	
}
