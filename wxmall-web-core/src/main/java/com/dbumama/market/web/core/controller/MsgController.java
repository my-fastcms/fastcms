/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.dbumama.market.web.core.controller;

import com.dbumama.market.web.core.interceptor.MsgInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.NotAction;
import com.jfinal.kit.HttpKit;
import com.jfinal.log.Log;
import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.api.ApiConfigKit;
import com.weixin.sdk.kit.MsgEncryptKit;
import com.weixin.sdk.msg.InMsgParser;
import com.weixin.sdk.msg.OutMsgXmlBuilder;
import com.weixin.sdk.msg.in.InImageMsg;
import com.weixin.sdk.msg.in.InLinkMsg;
import com.weixin.sdk.msg.in.InLocationMsg;
import com.weixin.sdk.msg.in.InMsg;
import com.weixin.sdk.msg.in.InShortVideoMsg;
import com.weixin.sdk.msg.in.InTextMsg;
import com.weixin.sdk.msg.in.InVideoMsg;
import com.weixin.sdk.msg.in.InVoiceMsg;
import com.weixin.sdk.msg.in.event.InCustomEvent;
import com.weixin.sdk.msg.in.event.InFollowEvent;
import com.weixin.sdk.msg.in.event.InLocationEvent;
import com.weixin.sdk.msg.in.event.InMassEvent;
import com.weixin.sdk.msg.in.event.InMenuEvent;
import com.weixin.sdk.msg.in.event.InPoiCheckNotifyEvent;
import com.weixin.sdk.msg.in.event.InQrCodeEvent;
import com.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent;
import com.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.weixin.sdk.msg.in.event.card.InCardPasscheckEvent;
import com.weixin.sdk.msg.in.event.card.InCardPayorderEvent;
import com.weixin.sdk.msg.in.event.card.InCardskuRemindEvent;
import com.weixin.sdk.msg.in.event.card.InSubmitMembercardUserinfoEvent;
import com.weixin.sdk.msg.in.event.card.InUpdateMembercardEvent;
import com.weixin.sdk.msg.in.event.card.InUserConsumecardEvent;
import com.weixin.sdk.msg.in.event.card.InUserDelcardEvent;
import com.weixin.sdk.msg.in.event.card.InUserEntersessionFromcardEvent;
import com.weixin.sdk.msg.in.event.card.InUserGetcardEvent;
import com.weixin.sdk.msg.in.event.card.InUserPayfromPaycellEvent;
import com.weixin.sdk.msg.in.event.card.InUserViewcardEvent;
import com.weixin.sdk.msg.in.event.weapp.InWeappAuditFailEvent;
import com.weixin.sdk.msg.in.event.weapp.InWeappAuditSuccessEvent;
import com.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.weixin.sdk.msg.out.OutMsg;
import com.weixin.sdk.msg.out.OutTextMsg;

/**
 * 接收微信服务器消息，自动解析成 InMsg 并分发到相应的处理方法
 */
public abstract class MsgController extends BaseController {
	
	private static final Log log =  Log.getLog(MsgController.class);
	private String inMsgXml = null;		// 本次请求 xml数据
	private InMsg inMsg = null;			// 本次请求 xml 解析后的 InMsg 对象
	
	public abstract ApiConfig getApiConfig();
	
	/**
	 * weixin 公众号服务器调用唯一入口，即在开发者中心输入的 URL 必须要指向此 action
	 */
	@Before(MsgInterceptor.class)
	public void index() {
		// 开发模式输出微信服务发送过来的  xml 消息
		if (ApiConfigKit.isDevMode()) {
			System.out.println("接收消息:");
			System.out.println(getInMsgXml());
		}
		
		// 解析消息并根据消息类型分发到相应的处理方法
		InMsg msg = getInMsg();
		if (msg instanceof InTextMsg)
			processInTextMsg((InTextMsg) msg);
		else if (msg instanceof InImageMsg)
			processInImageMsg((InImageMsg) msg);
		else if (msg instanceof InVoiceMsg)
			processInVoiceMsg((InVoiceMsg) msg);
		else if (msg instanceof InVideoMsg)
			processInVideoMsg((InVideoMsg) msg);
		else if (msg instanceof InShortVideoMsg)   //支持小视频
			processInShortVideoMsg((InShortVideoMsg) msg);
		else if (msg instanceof InLocationMsg)
			processInLocationMsg((InLocationMsg) msg);
		else if (msg instanceof InLinkMsg)
			processInLinkMsg((InLinkMsg) msg);
		else if (msg instanceof InCustomEvent)
			processInCustomEvent((InCustomEvent) msg);
		else if (msg instanceof InFollowEvent)
			processInFollowEvent((InFollowEvent) msg);
		else if (msg instanceof InQrCodeEvent)
			processInQrCodeEvent((InQrCodeEvent) msg);
		else if (msg instanceof InLocationEvent)
			processInLocationEvent((InLocationEvent) msg);
		else if (msg instanceof InMassEvent)
			processInMassEvent((InMassEvent) msg);
		else if (msg instanceof InMenuEvent)
			processInMenuEvent((InMenuEvent) msg);
		else if (msg instanceof InSpeechRecognitionResults)
			processInSpeechRecognitionResults((InSpeechRecognitionResults) msg);
		else if (msg instanceof InTemplateMsgEvent)
			processInTemplateMsgEvent((InTemplateMsgEvent) msg);
		else if (msg instanceof InShakearoundUserShakeEvent)
			processInShakearoundUserShakeEvent((InShakearoundUserShakeEvent) msg);
		else if (msg instanceof InVerifySuccessEvent)
			processInVerifySuccessEvent((InVerifySuccessEvent) msg);
		else if (msg instanceof InVerifyFailEvent)
			processInVerifyFailEvent((InVerifyFailEvent) msg);
		else if (msg instanceof InPoiCheckNotifyEvent)
			processInPoiCheckNotifyEvent((InPoiCheckNotifyEvent) msg);
		//卡券事件通知开始
		else if (msg instanceof InCardPasscheckEvent)
			processInCardPasscheckEvent((InCardPasscheckEvent) msg);
		else if (msg instanceof InUserDelcardEvent)
			processInUserDelcardEvent((InUserDelcardEvent) msg);
		else if (msg instanceof InUserGetcardEvent)
			processInUserGetcardEvent((InUserGetcardEvent) msg);
		else if (msg instanceof InUserViewcardEvent)
			processInUserViewcardEvent((InUserViewcardEvent) msg);
		else if (msg instanceof InCardPayorderEvent)
			processInCardPayorderEvent((InCardPayorderEvent) msg);
		else if (msg instanceof InCardskuRemindEvent)
			processInCardskuRemindEvent((InCardskuRemindEvent) msg);
		else if (msg instanceof InUpdateMembercardEvent)
			processInUpdateMembercardEvent((InUpdateMembercardEvent) msg);
		else if (msg instanceof InUserConsumecardEvent)
			processInUserConsumecardEvent((InUserConsumecardEvent) msg);
		else if (msg instanceof InUserEntersessionFromcardEvent)
			processInUserEntersessionFromcardEvent((InUserEntersessionFromcardEvent) msg);
		else if (msg instanceof InUserPayfromPaycellEvent)
			processInUserPayfromPaycellEvent((InUserPayfromPaycellEvent) msg);
		else if (msg instanceof InSubmitMembercardUserinfoEvent)
			processInSubmitMembercardUserinfoEvent((InSubmitMembercardUserinfoEvent) msg);
		//卡券事件通知结束
		else
			log.error("未能识别的消息类型。 消息 xml 内容为：\n" + getInMsgXml());
	}
	
	/**
	 * 在接收到微信服务器的 InMsg 消息后后响应 OutMsg 消息
	 */
	public void render(OutMsg outMsg) {
		String outMsgXml = OutMsgXmlBuilder.build(outMsg);
		// 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
		if (ApiConfigKit.isDevMode()) {
			System.out.println("发送消息:");
			System.out.println(outMsgXml);
			System.out.println("--------------------------------------------------------------------------------\n");
		}
		
		// 是否需要加密消息
		if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
			outMsgXml = MsgEncryptKit.encrypt(outMsgXml, getPara("timestamp"), getPara("nonce"));
		}
		
		renderText(outMsgXml, "text/xml");
	}
	
	public void renderOutTextMsg(String content) {
		OutTextMsg outMsg= new OutTextMsg(getInMsg());
		outMsg.setContent(content);
		render(outMsg);
	}
	
	@Before(NotAction.class)
	public String getInMsgXml() {
		if (inMsgXml == null) {
			inMsgXml = HttpKit.readData(getRequest());
			
			// 是否需要解密消息
			if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
				inMsgXml = MsgEncryptKit.decrypt(inMsgXml, getPara("timestamp"), getPara("nonce"), getPara("msg_signature"));
			}
		}
		return inMsgXml;
	}
	
	@Before(NotAction.class)
	public InMsg getInMsg() {
		if (inMsg == null)
			inMsg = InMsgParser.parse(getInMsgXml()); 
		return inMsg;
	}
	
	// 处理接收到的文本消息
	protected abstract void processInTextMsg(InTextMsg inTextMsg);
	
	// 处理接收到的图片消息
	protected abstract void processInImageMsg(InImageMsg inImageMsg);
	
	// 处理接收到的语音消息
	protected abstract void processInVoiceMsg(InVoiceMsg inVoiceMsg);
	
	// 处理接收到的视频消息
	protected abstract void processInVideoMsg(InVideoMsg inVideoMsg);

	// 处理接收到的视频消息
	protected abstract void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg);
	
	// 处理接收到的地址位置消息
	protected abstract void processInLocationMsg(InLocationMsg inLocationMsg);

	// 处理接收到的链接消息
	protected abstract void processInLinkMsg(InLinkMsg inLinkMsg);

    // 处理接收到的多客服管理事件
    protected abstract void processInCustomEvent(InCustomEvent inCustomEvent);

	// 处理接收到的关注/取消关注事件
	protected abstract void processInFollowEvent(InFollowEvent inFollowEvent);
	
	// 处理接收到的扫描带参数二维码事件
	protected abstract void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent);
	
	// 处理接收到的上报地理位置事件
	protected abstract void processInLocationEvent(InLocationEvent inLocationEvent);

    // 处理接收到的群发任务结束时通知事件
    protected abstract void processInMassEvent(InMassEvent inMassEvent);

	// 处理接收到的自定义菜单事件
	protected abstract void processInMenuEvent(InMenuEvent inMenuEvent);
	
	// 处理接收到的语音识别结果
	protected abstract void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults);
	
	// 处理接收到的模板消息是否送达成功通知事件
	protected abstract void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent);

	// 处理微信摇一摇事件
	protected abstract void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent);

	// 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
	protected abstract void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent);

	// 资质认证失败 || 名称认证失败
	protected abstract void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent);
	
	// 门店在审核事件消息
	protected abstract void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent);
	
	//卡券事件
	protected abstract void processInCardPasscheckEvent(InCardPasscheckEvent inCardPasscheckEvent);
	protected abstract void processInUserDelcardEvent(InUserDelcardEvent inUserDelcardEvent);
	protected abstract void processInUpdateMembercardEvent(InUpdateMembercardEvent inUpdateMembercardEvent);
	protected abstract void processInCardPayorderEvent(InCardPayorderEvent inCardPayorderEvent);
	protected abstract void processInCardskuRemindEvent(InCardskuRemindEvent event);
	protected abstract void processInUserConsumecardEvent(InUserConsumecardEvent event);
	protected abstract void processInUserEntersessionFromcardEvent(InUserEntersessionFromcardEvent event);
	protected abstract void processInUserGetcardEvent(InUserGetcardEvent event);
	protected abstract void processInUserViewcardEvent(InUserViewcardEvent event);
	protected abstract void processInUserPayfromPaycellEvent(InUserPayfromPaycellEvent event);
	protected abstract void processInSubmitMembercardUserinfoEvent(InSubmitMembercardUserinfoEvent event);
	
	//小程序审核事件
	protected abstract void processInWeappAduitSuccessEvent(InWeappAuditSuccessEvent event);
	protected abstract void processInWeappAduitFailEvent(InWeappAuditFailEvent event);
	
}













