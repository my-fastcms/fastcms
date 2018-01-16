package com.weixin.sdk.msg.in.event.card;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 
 * @author wangjun
 *<xml> 
  <ToUserName> <![CDATA[gh_3fcea188bf78]]></ToUserName>  
  <FromUserName><![CDATA[obLatjlaNQKb8FqOvt1M1x1lIBFE]]></FromUserName>  
  <CreateTime>1432668700</CreateTime>  
  <MsgType><![CDATA[event]]></MsgType>  
  <Event><![CDATA[submit_membercard_user_info]]></Event>  
  <CardId><![CDATA[pbLatjtZ7v1BG_ZnTjbW85GYc_E8]]></CardId>  
  <UserCardCode><![CDATA[018255396048]]></UserCardCode> 
</xml>
 */
public class InSubmitMembercardUserinfoEvent extends EventInMsg{

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

	public InSubmitMembercardUserinfoEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
