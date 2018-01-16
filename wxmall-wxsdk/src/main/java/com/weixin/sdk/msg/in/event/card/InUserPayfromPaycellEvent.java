package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 用户使用卡券买单事件推送
 * @author wangjun
 *<xml>
<ToUserName><![CDATA[gh_e2243xxxxxxx]]></ToUserName>
<FromUserName><![CDATA[oo2VNuOUuZGMxxxxxxxx]]></FromUserName>
<CreateTime>1442390947</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[user_pay_from_pay_cell]]></Event>
<CardId><![CDATA[po2VNuCuRo-8sxxxxxxxxxxx]]></CardId>
<UserCardCode><![CDATA[38050000000]]></UserCardCode>
<TransId><![CDATA[10022403432015000000000]]></TransId>
<LocationId>291710000</LocationId>
<Fee><![CDATA[10000]]></Fee>
<OriginalFee><![CDATA[10000]]> </OriginalFee>
</xml>
 */
public class InUserPayfromPaycellEvent extends EventInMsg{

	private String cardId;
	private String userCardCode;
	private String transId;
	private String locationId;
	private String fee;
	private String originalFee;
	
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

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getOriginalFee() {
		return originalFee;
	}

	public void setOriginalFee(String originalFee) {
		this.originalFee = originalFee;
	}

	public InUserPayfromPaycellEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
