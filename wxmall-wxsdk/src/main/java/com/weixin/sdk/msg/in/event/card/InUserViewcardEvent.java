package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 用户进入卡券事件推送
 * @author wangjun
 *<xml> <ToUserName><![CDATA[toUser]]></ToUserName> 
<FromUserName><![CDATA[FromUser]]></FromUserName> 
<CreateTime>123456789</CreateTime> 
<MsgType><![CDATA[event]]></MsgType> 
<Event><![CDATA[user_view_card]]></Event> 
<CardId><![CDATA[cardid]]></CardId> 
<UserCardCode><![CDATA[12312312]]></UserCardCode>
</xml>
 */
public class InUserViewcardEvent extends EventInMsg{

	private String cardId;
	private String userCardCode;
	
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

	public InUserViewcardEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
