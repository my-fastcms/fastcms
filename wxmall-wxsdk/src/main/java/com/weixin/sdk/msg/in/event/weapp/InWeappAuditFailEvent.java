package com.weixin.sdk.msg.in.event.weapp;

import com.weixin.sdk.msg.in.event.EventInMsg;

public class InWeappAuditFailEvent extends EventInMsg{

	String reason;
	String failTime;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFailTime() {
		return failTime;
	}
	public void setFailTime(String failTime) {
		this.failTime = failTime;
	}

	public InWeappAuditFailEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
