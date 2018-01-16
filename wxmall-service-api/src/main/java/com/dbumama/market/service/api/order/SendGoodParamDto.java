package com.dbumama.market.service.api.order;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class SendGoodParamDto extends AbstractParamDto{

	private Long orderId;
	private Long buyerId;
	private String expKey;
	private String billNumber;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public String getExpKey() {
		return expKey;
	}
	public void setExpKey(String expKey) {
		this.expKey = expKey;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	
}
