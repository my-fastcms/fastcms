package com.dbumama.market.service.api.order;

@SuppressWarnings("serial")
public class OrderJoinParamDto extends OrderCreateParamDto{

	//组团发起者
	private Long groupUserId;

	public OrderJoinParamDto(Long sellerId, Long groupUserId, Long buyerId, Long receiverId, String items) {
		super(sellerId, buyerId, receiverId, items);
		this.groupUserId = groupUserId;
	}
	
	public Long getGroupUserId() {
		return groupUserId;
	}
	public void setGroupUserId(Long groupUserId) {
		this.groupUserId = groupUserId;
	}

}
