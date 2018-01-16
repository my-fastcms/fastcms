package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 核销卡券事件
 * @author wangjun
 *<xml> <ToUserName><![CDATA[toUser]]></ToUserName> 
<FromUserName><![CDATA[FromUser]]></FromUserName> 
<CreateTime>123456789</CreateTime> 
<MsgType><![CDATA[event]]></MsgType> 
<Event><![CDATA[user_consume_card]]></Event> 
<CardId><![CDATA[cardid]]></CardId> 
<UserCardCode><![CDATA[12312312]]></UserCardCode>
<ConsumeSource><![CDATA[(FROM_API)]]></ConsumeSource>
<OutTradeNo><![CDATA[aaaaaaaaaaaa]]></OutTradeNo>
<TransId><![CDATA[bbbbbbbbbb]]></TransId>
<LocationId><![CDATA[222222]]></LocationId>
<StaffOpenId><![CDATA[obLatjjwDolFjRRd3doGIdwNqRXw]></StaffOpenId>
</xml>
 */
public class InUserConsumecardEvent extends EventInMsg{

	private String cardId;
	private String userCardCode;
	private String consumeSource;
	private String outTradeNo;
	private String transId;
	private String locationId;
	private String staffOpenId;
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserCardCode() {
		return userCardCode;
	}

	public void setUserCardCode(String userCardCode) {
		this.userCardCode = userCardCode;
	}

	public String getConsumeSource() {
		return consumeSource;
	}

	public void setConsumeSource(String consumeSource) {
		this.consumeSource = consumeSource;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getStaffOpenId() {
		return staffOpenId;
	}

	public void setStaffOpenId(String staffOpenId) {
		this.staffOpenId = staffOpenId;
	}
	
	public InUserConsumecardEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
