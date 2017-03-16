package com.dbumama.market.service.api.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dbumama.market.service.api.product.ProductFullCutResultDto;
import com.dbumama.market.service.common.AbstractResultDto;

@SuppressWarnings("serial")
public class OrderResultDto extends AbstractResultDto{

	protected Long orderId;
	protected String receiverName;
	protected String receiverPhone;
	protected String receiverProvince;			//收件人所在省
	protected String receiverCity;				//收件人所在市
	protected String receiverCountry;			//收件人所在县区
	protected String receiverAddr;				//收件人详细地址
	protected String zipCode;					//收件人邮编
	protected String buyerNick;					//买家昵称
	protected String buyerMemo;					//买家留言
	protected Integer num;						//订单项中 商品总数
	protected BigDecimal totalPrice;			//商品总价  订单项中商品单价 * 商品数量 的价格和
	protected BigDecimal oldPrice;				//原商品总价， 打折前，满减前的商品总价格
	protected BigDecimal oldPostFee;			//原邮费 满减前的邮费
	protected BigDecimal postFee;				//运费
	protected BigDecimal payFee;				//应付金额
	protected String orderSn;					//订单编号
	protected Date orderCreated;				//订单创建时间
	protected String orderStatus;				//订单状态
	protected String transactionId;             //退款标识
	protected List<OrderItemResultDto> orderItems;
	protected List<ProductFullCutResultDto> allItemsFullCuts; //订单中所有商品的满减数据集合，通过从小到大排序

	public OrderResultDto(){
		setBuyerNick("");
		setBuyerMemo("");
		setZipCode("");
	}
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	public Date getOrderCreated() {
		return orderCreated;
	}
	public void setOrderCreated(Date orderCreated) {
		this.orderCreated = orderCreated;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public List<OrderItemResultDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemResultDto> orderItems) {
		this.orderItems = orderItems;
	}
	public List<ProductFullCutResultDto> getAllItemsFullCuts() {
		return allItemsFullCuts;
	}
	public void setAllItemsFullCuts(List<ProductFullCutResultDto> allItemsFullCuts) {
		this.allItemsFullCuts = allItemsFullCuts;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
