package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 券点流水详情事件
 * @author wangjun
 *<xml>
 <ToUserName><![CDATA[gh_7223c83d4be5]]></ToUserName>
 <FromUserName><![CDATA[ob5E7s-HoN9tslQY3-0I4qmgluHk]]></FromUserName>
 <CreateTime>1453295737</CreateTime>
 <MsgType><![CDATA[event]]></MsgType>
 <Event><![CDATA[card_pay_order]]></Event>
 <OrderId><![CDATA[404091456]]></OrderId>
 <Status><![CDATA[ORDER_STATUS_FINANCE_SUCC]]></Status>
 <CreateTime>1453295737</CreateTime>
 <PayFinishTime>0</PayFinishTime>
 <Desc><![CDATA[]]></Desc>
 <FreeCoinCount><![CDATA[200]]></FreeCoinCount>
 <PayCoinCount><![CDATA[0]]></PayCoinCount>
 <RefundFreeCoinCount><![CDATA[0]]></RefundFreeCoinCount>
 <RefundPayCoinCount><![CDATA[0]]></RefundPayCoinCount>
 <OrderType><![CDATA[ORDER_TYPE_SYS_ADD]]></OrderType>
 <Memo><![CDATA[开通账户奖励]]></Memo>
 <ReceiptInfo><![CDATA[]]></ReceiptInfo>
</xml>
 */
public class InCardPayorderEvent extends EventInMsg{

	private String orderId;
	private String status;
	private String payFinishTime;
	private String desc;
	private String freeCoinCount;
	private String payCoinCount;
	private String refundFreeCoinCount;
	private String refundPayCoinCount;
	private String orderType;
	private String memo;
	private String receiptInfo;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(String payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFreeCoinCount() {
		return freeCoinCount;
	}

	public void setFreeCoinCount(String freeCoinCount) {
		this.freeCoinCount = freeCoinCount;
	}

	public String getPayCoinCount() {
		return payCoinCount;
	}

	public void setPayCoinCount(String payCoinCount) {
		this.payCoinCount = payCoinCount;
	}

	public String getRefundFreeCoinCount() {
		return refundFreeCoinCount;
	}

	public void setRefundFreeCoinCount(String refundFreeCoinCount) {
		this.refundFreeCoinCount = refundFreeCoinCount;
	}

	public String getRefundPayCoinCount() {
		return refundPayCoinCount;
	}

	public void setRefundPayCoinCount(String refundPayCoinCount) {
		this.refundPayCoinCount = refundPayCoinCount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getReceiptInfo() {
		return receiptInfo;
	}

	public void setReceiptInfo(String receiptInfo) {
		this.receiptInfo = receiptInfo;
	}

	public InCardPayorderEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
