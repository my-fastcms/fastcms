package com.dbumama.market.service.api.order;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class OrderPayResultDto extends AbstractResultDto{

	private BigDecimal payFee;
	private String tradeNo;
	private String orderSn;
	public BigDecimal getPayFee() {
		return payFee;
	}
	public void setPayFee(BigDecimal payFee) {
		this.payFee = payFee;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}
	
}
