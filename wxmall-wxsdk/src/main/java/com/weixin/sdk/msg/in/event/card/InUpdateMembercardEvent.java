package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 会员卡内容更新事件
 * @author wangjun
 *<xml><ToUserName><![CDATA[gh_9e1765b5568e]]></ToUserName>
<FromUserName><![CDATA[ojZ8YtyVyr30HheH3CM73y7h4jJE]]></FromUserName>
<CreateTime>1445507140</CreateTime>
<MsgType><![CDATA[event]]></MsgType>
<Event><![CDATA[update_member_card]]></Event>
<CardId><![CDATA[pjZ8Ytx-nwvpCRyQneH3Ncmh6N94]]></CardId>
<UserCardCode><![CDATA[485027611252]]></UserCardCode>
<ModifyBonus>3</ModifyBonus>
<ModifyBalance>0</ModifyBalance>
</xml>
 */
public class InUpdateMembercardEvent extends EventInMsg{

	private String cardId;
	private String userCardCode;
	private String modifyBonus;
	private String modifyBalance;
	
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

	public String getModifyBonus() {
		return modifyBonus;
	}

	public void setModifyBonus(String modifyBonus) {
		this.modifyBonus = modifyBonus;
	}

	public String getModifyBalance() {
		return modifyBalance;
	}

	public void setModifyBalance(String modifyBalance) {
		this.modifyBalance = modifyBalance;
	}

	public InUpdateMembercardEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
