/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.msg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.weixin.sdk.kit.XmlKit;
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
import com.weixin.sdk.msg.in.event.InShakearoundUserShakeEvent.AroundBeacon;
import com.weixin.sdk.msg.in.event.InTemplateMsgEvent;
import com.weixin.sdk.msg.in.event.InVerifyFailEvent;
import com.weixin.sdk.msg.in.event.InVerifySuccessEvent;
import com.weixin.sdk.msg.in.event.ScanCodeInfo;
import com.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;

public class InMsgParser {
	
	private InMsgParser() {}
	
	/**
	 * 从 xml 中解析出各类消息与事件
	 */
	public static InMsg parse(String xml) {
		Document doc = XmlKit.parse(xml);
		return doParse(doc);
	}
	
	/**
	 * 消息类型
	 * 1：text 文本消息
	 * 2：image 图片消息
	 * 3：voice 语音消息
	 * 4：video 视频消息
	 *	shortvideo 小视频消息
	 * 5：location 地址位置消息
	 * 6：link 链接消息
	 * 7：event 事件
	 */
	private static InMsg doParse(Document doc) {
		Element root = doc.getDocumentElement();
		
		String toUserName = XmlKit.elementText(root, "ToUserName");
		String fromUserName = XmlKit.elementText(root, "FromUserName");
		Integer createTime = Integer.parseInt(XmlKit.elementText(root, "CreateTime"));
		String msgType = XmlKit.elementText(root, "MsgType");
		if ("text".equals(msgType))
			return parseInTextMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("image".equals(msgType))
			return parseInImageMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("voice".equals(msgType))
			return parseInVoiceMsgAndInSpeechRecognitionResults(root, toUserName, fromUserName, createTime, msgType);
		if ("video".equals(msgType))
			return parseInVideoMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("shortvideo".equals(msgType))	 //支持小视频
			return parseInShortVideoMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("location".equals(msgType))
			return parseInLocationMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("link".equals(msgType))
			return parseInLinkMsg(root, toUserName, fromUserName, createTime, msgType);
		if ("event".equals(msgType))
			return parseInEvent(root, toUserName, fromUserName, createTime, msgType);
		throw new RuntimeException("无法识别的消息类型 " + msgType + "，请查阅微信公众平台开发文档");
	}
	
	private static InMsg parseInTextMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InTextMsg msg = new InTextMsg(toUserName, fromUserName, createTime, msgType);
		msg.setContent(XmlKit.elementText(root, "Content"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}
	
	private static InMsg parseInImageMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InImageMsg msg = new InImageMsg(toUserName, fromUserName, createTime, msgType);
		msg.setPicUrl(XmlKit.elementText(root, "PicUrl"));
		msg.setMediaId(XmlKit.elementText(root, "MediaId"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}
	
	private static InMsg parseInVoiceMsgAndInSpeechRecognitionResults(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		String recognition = XmlKit.elementText(root, "Recognition");
		if (StringUtils.isBlank(recognition)) {
			InVoiceMsg msg = new InVoiceMsg(toUserName, fromUserName, createTime, msgType);
			msg.setMediaId(XmlKit.elementText(root, "MediaId"));
			msg.setFormat(XmlKit.elementText(root, "Format"));
			msg.setMsgId(XmlKit.elementText(root, "MsgId"));
			return msg;
		}
		else {
			InSpeechRecognitionResults msg = new InSpeechRecognitionResults(toUserName, fromUserName, createTime, msgType);
			msg.setMediaId(XmlKit.elementText(root, "MediaId"));
			msg.setFormat(XmlKit.elementText(root, "Format"));
			msg.setMsgId(XmlKit.elementText(root, "MsgId"));
			msg.setRecognition(recognition);			// 与 InVoiceMsg 唯一的不同之处
			return msg;
		}
	}
	
	private static InMsg parseInVideoMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InVideoMsg msg = new InVideoMsg(toUserName, fromUserName, createTime, msgType);
		msg.setMediaId(XmlKit.elementText(root, "MediaId"));
		msg.setThumbMediaId(XmlKit.elementText(root, "ThumbMediaId"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}

	private static InMsg parseInShortVideoMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InShortVideoMsg msg = new InShortVideoMsg(toUserName, fromUserName, createTime, msgType);
		msg.setMediaId(XmlKit.elementText(root, "MediaId"));
		msg.setThumbMediaId(XmlKit.elementText(root, "ThumbMediaId"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}

	private static InMsg parseInLocationMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InLocationMsg msg = new InLocationMsg(toUserName, fromUserName, createTime, msgType);
		msg.setLocation_X(XmlKit.elementText(root, "Location_X"));
		msg.setLocation_Y(XmlKit.elementText(root, "Location_Y"));
		msg.setScale(XmlKit.elementText(root, "Scale"));
		msg.setLabel(XmlKit.elementText(root, "Label"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}
	
	private static InMsg parseInLinkMsg(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		InLinkMsg msg = new InLinkMsg(toUserName, fromUserName, createTime, msgType);
		msg.setTitle(XmlKit.elementText(root, "Title"));
		msg.setDescription(XmlKit.elementText(root, "Description"));
		msg.setUrl(XmlKit.elementText(root, "Url"));
		msg.setMsgId(XmlKit.elementText(root, "MsgId"));
		return msg;
	}

	// 解析各种事件
	private static InMsg parseInEvent(Element root, String toUserName, String fromUserName, Integer createTime, String msgType) {
		String event = XmlKit.elementText(root, "Event");
		String eventKey = XmlKit.elementText(root, "EventKey");
		
		// 关注/取消关注事件（包括二维码扫描关注，二维码扫描关注事件与扫描带参数二维码事件是两回事）
		if (("subscribe".equals(event) || "unsubscribe".equals(event)) && StringUtils.isBlank(eventKey)) {
			return new InFollowEvent(toUserName, fromUserName, createTime, msgType, event);
		}
		// 扫描带参数二维码事件之一		1: 用户未关注时，进行关注后的事件推送
		String ticket = XmlKit.elementText(root, "Ticket");
		if ("subscribe".equals(event) && StringUtils.isNotBlank(eventKey) && eventKey.startsWith("qrscene_")) {
			InQrCodeEvent e = new InQrCodeEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			e.setTicket(ticket);
			return e;
		}
		// 扫描带参数二维码事件之二		2: 用户已关注时的事件推送
		if ("SCAN".equals(event)) {
			InQrCodeEvent e = new InQrCodeEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			e.setTicket(ticket);
			return e;
		}
		// 上报地理位置事件
		if ("LOCATION".equals(event)) {
			InLocationEvent e = new InLocationEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setLatitude(XmlKit.elementText(root, "Latitude"));
			e.setLongitude(XmlKit.elementText(root, "Longitude"));
			e.setPrecision(XmlKit.elementText(root, "Precision"));
			return e;
		}
		// 自定义菜单事件之一			1：点击菜单拉取消息时的事件推送
		if ("CLICK".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// 自定义菜单事件之二			2：点击菜单跳转链接时的事件推送
		if ("VIEW".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// 扫码推事件
		if ("scancode_push".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			Document scanCodeInfo = XmlKit.element(root, "ScanCodeInfo");
			String scanType = XmlKit.documentText(scanCodeInfo, "ScanType");
			String scanResult = XmlKit.documentText(scanCodeInfo, "ScanResult");
			e.setScanCodeInfo(new ScanCodeInfo(scanType, scanResult));
			return e;
		}
		// 扫码推事件且弹出“消息接收中”提示框
		if ("scancode_waitmsg".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			Document scanCodeInfo = XmlKit.element(root, "ScanCodeInfo");
			String scanType = XmlKit.documentText(scanCodeInfo, "ScanType");
			String scanResult = XmlKit.documentText(scanCodeInfo, "ScanResult");
			e.setScanCodeInfo(new ScanCodeInfo(scanType, scanResult));
			return e;
		}
		// 5. pic_sysphoto：弹出系统拍照发图，这个后台其实收不到该菜单的消息，点击它后，调用的是手机里面的照相机功能，而照相以后再发过来时，就收到的是一个图片消息了
		if ("pic_sysphoto".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// pic_photo_or_album：弹出拍照或者相册发图
		if ("pic_photo_or_album".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// pic_weixin：弹出微信相册发图器
		if ("pic_weixin".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// location_select：弹出地理位置选择器
		if ("location_select".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// media_id：下发消息（除文本消息）
		if ("media_id".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// view_limited：跳转图文消息URL
		if ("view_limited".equals(event)) {
			InMenuEvent e = new InMenuEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setEventKey(eventKey);
			return e;
		}
		// 模板消息是否送达成功通知事件
		if ("TEMPLATESENDJOBFINISH".equals(event)) {
			InTemplateMsgEvent e = new InTemplateMsgEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setMsgId(XmlKit.elementText(root, "MsgID"));
			e.setStatus(XmlKit.elementText(root, "Status"));
			return e;
		}
		// 群发任务结束时是否送达成功通知事件
		if ("MASSSENDJOBFINISH".equals(event)) {
			InMassEvent e = new InMassEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setMsgId(XmlKit.elementText(root, "MsgID"));
			e.setStatus(XmlKit.elementText(root, "Status"));
			e.setTotalCount(XmlKit.elementText(root, "TotalCount"));
			e.setFilterCount(XmlKit.elementText(root, "FilterCount"));
			e.setSentCount(XmlKit.elementText(root, "SentCount"));
			e.setErrorCount(XmlKit.elementText(root, "ErrorCount"));
			return e;
		}
		// 多客服接入会话事件
		if ("kf_create_session".equals(event)) {
			InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setKfAccount(XmlKit.elementText(root, "KfAccount"));
			return e;
		}
		// 多客服关闭会话事件
		if ("kf_close_session".equals(event)) {
			InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setKfAccount(XmlKit.elementText(root, "KfAccount"));
			return e;
		}
		// 多客服转接会话事件
		if ("kf_switch_session".equals(event)) {
			InCustomEvent e = new InCustomEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setKfAccount(XmlKit.elementText(root, "KfAccount"));
			e.setToKfAccount(XmlKit.elementText(root, "ToKfAccount"));
			return e;
		}
		// 微信摇一摇事件
		if ("ShakearoundUserShake".equals(event)){
			InShakearoundUserShakeEvent e = new InShakearoundUserShakeEvent(toUserName, fromUserName, createTime, msgType);
			e.setEvent(event);
			Document c = XmlKit.element(root, "ChosenBeacon");
			e.setUuid(XmlKit.documentText(c, "Uuid"));
			e.setMajor(Integer.parseInt(XmlKit.documentText(c, "Major")));
			e.setMinor(Integer.parseInt(XmlKit.documentText(c, "Minor")));
			e.setDistance(Float.parseFloat(XmlKit.documentText(c, "Distance")));

			NodeList nodeList = root.getElementsByTagName("AroundBeacon");
			if (nodeList != null && nodeList.getLength() > 0) {
				AroundBeacon aroundBeacon = null;
				List<AroundBeacon> aroundBeacons = new ArrayList<AroundBeacon>();
				for (int i = 0; i < nodeList.getLength(); i++) {
					Document nodeDoc = nodeList.item(i).getOwnerDocument();
					
					aroundBeacon = new AroundBeacon();
					aroundBeacon.setUuid(XmlKit.documentText(nodeDoc, "Uuid"));
					aroundBeacon.setMajor(Integer.parseInt(XmlKit.documentText(nodeDoc, "Major")));
					aroundBeacon.setMinor(Integer.parseInt(XmlKit.documentText(nodeDoc, "Minor")));
					aroundBeacon.setDistance(Float.parseFloat(XmlKit.documentText(nodeDoc, "Distance")));
					aroundBeacons.add(aroundBeacon);
				}
				e.setAroundBeaconList(aroundBeacons);
			}
			return e;
		 }

		// 资质认证成功 || 名称认证成功 || 年审通知 || 认证过期失效通知
		if ("qualification_verify_success".equals(event) || "naming_verify_success".equals(event)
				 || "annual_renew".equals(event) || "verify_expired".equals(event)) {
			InVerifySuccessEvent e = new InVerifySuccessEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setExpiredTime(XmlKit.elementText(root, "expiredTime"));
			return e;
		}
		// 资质认证失败 || 名称认证失败
		if ("qualification_verify_fail".equals(event) || "naming_verify_fail".equals(event)) {
			InVerifyFailEvent e = new InVerifyFailEvent(toUserName, fromUserName, createTime, msgType, event);
			e.setFailTime(XmlKit.elementText(root, "failTime"));
			e.setFailReason(XmlKit.elementText(root, "failReason"));
			return e;
		}
		// 门店在审核事件消息
		if ("poi_check_notify".equals(event)) {
			InPoiCheckNotifyEvent e = new InPoiCheckNotifyEvent(toUserName, fromUserName, createTime, msgType);
			e.setUniqId(XmlKit.elementText(root, "UniqId"));
			e.setPoiId(XmlKit.elementText(root, "PoiId"));
			e.setResult(XmlKit.elementText(root, "Result"));
			e.setMsg(XmlKit.elementText(root, "Msg"));
			return e;
		}

		throw new RuntimeException("无法识别的事件类型" + event + "，请查阅微信公众平台开发文档");
	}

}