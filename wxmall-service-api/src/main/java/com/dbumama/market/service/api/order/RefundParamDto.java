package com.dbumama.market.service.api.order;

import java.math.BigDecimal;

import com.dbumama.market.service.api.common.AbstractParamDto;

@SuppressWarnings("serial")
public class RefundParamDto extends AbstractParamDto {

	private Long orderId;
	private BigDecimal refundFee; //退款金额
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getRefundFee() {
		return refundFee;
	}
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}
	
}
