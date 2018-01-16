package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 库存报警事件
 * @author wangjun
 *<xml>
<ToUserName><![CDATA[gh_2d62d*****0]]></ToUserName>
<FromUserName><![CDATA[oa3LFuBvWb7*********]]></FromUserName> 
<CreateTime>1443838506</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[card_sku_remind]]></Event>
<CardId><![CDATA[pa3LFuAh2P65**********]]></CardId>
<Detail><![CDATA[the card's quantity is equal to 0]]></Detail>
</xml>
 */
public class InCardskuRemindEvent extends EventInMsg{

	private String cardId;
	private String detail;
	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public InCardskuRemindEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
