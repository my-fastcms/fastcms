package com.weixin.sdk.msg.in.event.weapp;

import com.weixin.sdk.msg.in.event.EventInMsg;

/**
 * 小程序提交代码版本审核成功事件
 * @author wangjun
 *
 */
public class InWeappAuditSuccessEvent extends EventInMsg{

	String succTime;
	public String getSuccTime() {
		return succTime;
	}
	public void setSuccTime(String succTime) {
		this.succTime = succTime;
	}
	
	public InWeappAuditSuccessEvent(String toUserName, String fromUserName, Integer createTime, String msgType,
			String event) {
		super(toUserName, fromUserName, createTime, msgType, event);
	}

}
