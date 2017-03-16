package com.dbumama.market.service.api.order;

import com.dbumama.market.service.common.AbstractParamDto;

@SuppressWarnings("serial")
public class OrderCreateParamDto extends AbstractParamDto{

	private Long sellerId;		
	private Long buyerId;			//下单者
	private Long receiverId;		//收货地址
	private String memo;			//买家留言
	private String items;			//订单项
	private String invoiceTitle;	//发票抬头
	private String isInvoice;		//是否需要发票抬头		
	
	public OrderCreateParamDto(Long sellerId, Long buyerId, Long receiverId, String items) {
		super();
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.receiverId = receiverId;
		this.items = items;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public Long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}
}
