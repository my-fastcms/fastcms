package com.dbumama.market.service.api.order;

import com.dbumama.market.service.common.AbstractPageParamDto;

@SuppressWarnings("serial")
public class OrderListParamDto extends AbstractPageParamDto{

	private Long sellerId;
	private Long buyerId;
	private String startDate;		//下单开始时间
	private String endDate;			//下单结束时间
	private String buyerNickName;	//下单人昵称
	private String receiverName;	//收货人
	private String receiverPhone;	//收货人手机
	private String orderStatus;		//订单状态
	private String paymentStatus;	//支付状态
	private String shippingStatus;	//发货状态
	
	public OrderListParamDto(Long sellerId, Integer pageNo) {
		super(pageNo);
		this.sellerId = sellerId;
	}
	
	public OrderListParamDto(Long sellerId, Long buyerId, Integer pageNo) {
		super(pageNo);
		this.sellerId = sellerId;
		this.buyerId = buyerId;
	}
	
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getBuyerNickName() {
		return buyerNickName;
	}
	public void setBuyerNickName(String buyerNickName) {
		this.buyerNickName = buyerNickName;
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getShippingStatus() {
		return shippingStatus;
	}
	public void setShippingStatus(String shippingStatus) {
		this.shippingStatus = shippingStatus;
	}
	
}
