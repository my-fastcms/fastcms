package com.dbumama.market.service.api.order;

import java.util.Date;
import java.util.List;

import com.dbumama.market.service.api.common.AbstractResultDto;
/**
 * 拼团订单Dto
 * @author drs
 *
 */
@SuppressWarnings("serial")
public class OrderTuanResultDto  extends AbstractResultDto{
	protected Long orderId;
	protected String orderSn;					//订单编号
	protected Date orderCreated;				//订单创建时间
	protected String orderStatus;				//订单状态
	protected String groupInfo;                 //拼团信息
	protected String groupStatus;               //组团状态
	protected List<OrderTuanGuserInfoDto> guserInfoItems;   //组团成员详细信息
	protected List<OrderItemResultDto> orderItems;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getGroupInfo() {
		return groupInfo;
	}
	public void setGroupInfo(String groupInfo) {
		this.groupInfo = groupInfo;
	}
	public String getGroupStatus() {
		return groupStatus;
	}
	public void setGroupStatus(String groupStatus) {
		this.groupStatus = groupStatus;
	}
	public List<OrderTuanGuserInfoDto> getGuserInfoItems() {
		return guserInfoItems;
	}
	public void setGuserInfoItems(List<OrderTuanGuserInfoDto> guserInfoItems) {
		this.guserInfoItems = guserInfoItems;
	}
	public List<OrderItemResultDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemResultDto> orderItems) {
		this.orderItems = orderItems;
	}
	
}
