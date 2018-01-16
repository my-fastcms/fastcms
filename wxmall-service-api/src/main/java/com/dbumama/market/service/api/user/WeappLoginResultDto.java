package com.dbumama.market.service.api.user;

import com.dbumama.market.service.api.common.AbstractResultDto;

@SuppressWarnings("serial")
public class WeappLoginResultDto extends AbstractResultDto{

	private String openid;			//微信小程序专用
	private String sessionKey;		//微信小程序专用
	private String sessionId;		//接口服务器登陆态sessionId，登陆微信服务器成功后，由接口服务器创建，并返回给客户端
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
