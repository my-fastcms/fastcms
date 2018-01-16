package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;
/**
 * 从卡券进入公众号会话事件推送
 * @author wangjun
 *<xml> <ToUserName><![CDATA[toUser]]></ToUserName> 
<FromUserName><![CDATA[FromUser]]></FromUserName> 
<CreateTime>123456789</CreateTime> 
<MsgType><![CDATA[event]]></MsgType> 
<Event><![CDATA[user_enter_session_from_card]]></Event> 
<CardId><![CDATA[cardid]]></CardId> 
<UserCardCode><![CDATA[12312312]]></UserCardCode>
</xml>
 */
public class InUserEntersessionFromcardEvent extends EventInMsg{

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

	public InUserEntersessionFromcardEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
