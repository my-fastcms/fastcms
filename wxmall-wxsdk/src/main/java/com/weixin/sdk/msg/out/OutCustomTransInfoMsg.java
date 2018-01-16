package com.weixin.sdk.msg.out;

import com.weixin.sdk.msg.in.InMsg;

/**
 * 转发到指定客服
 * @author wangjun
 *
 */
public class OutCustomTransInfoMsg extends OutCustomMsg{
	
	public static final String TEMPLATE =
			"<xml>\n" +
			"<ToUserName><![CDATA[${__msg.toUserName}]]></ToUserName>\n" +
			"<FromUserName><![CDATA[${__msg.fromUserName}]]></FromUserName>\n" +
			"<CreateTime>${__msg.createTime}</CreateTime>\n" +
			"<MsgType><![CDATA[${__msg.msgType}]]></MsgType>\n" +
			"<TransInfo>" + 
	        "<KfAccount><![CDATA[${__msg.kfAccount}]]></KfAccount>" + 
	        "</TransInfo>" +
			"</xml>";
	
	public OutCustomTransInfoMsg(){
		super();
	}
	
	public OutCustomTransInfoMsg(InMsg inMsg) {
		super(inMsg);
	}
	
	private String kfAccount;

	public String getKfAccount() {
		return kfAccount;
	}

	public OutCustomTransInfoMsg setKfAccount(String kfAccount) {
		this.kfAccount = kfAccount;
		return this;
	}
	
}
