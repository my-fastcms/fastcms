/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.msg.in.speech_recognition;

import com.weixin.sdk.msg.in.InVoiceMsg;


/**
	接收语音识别结果，与 InVoiceMsg 唯一的不同是多了一个 Recognition 标记
	<xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>1357290913</CreateTime>
		<MsgType><![CDATA[voice]]></MsgType>
			<MediaId><![CDATA[media_id]]></MediaId>
			<Format><![CDATA[Format]]></Format>
			<Recognition><![CDATA[腾讯微信团队]]></Recognition>
			<MsgId>1234567890123456</MsgId>
	</xml>
 */
public class InSpeechRecognitionResults extends InVoiceMsg {
	
	private String recognition;
	
	public InSpeechRecognitionResults(String toUserName, String fromUserName, Integer createTime, String msgType) {
		super(toUserName, fromUserName, createTime, msgType);
	}
	
	public String getRecognition() {
		return recognition;
	}
	
	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}
	
}
