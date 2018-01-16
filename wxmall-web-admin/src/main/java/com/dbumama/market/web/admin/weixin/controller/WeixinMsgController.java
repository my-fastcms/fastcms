package com.dbumama.market.web.admin.weixin.controller;

import java.util.List;

import com.dbumama.market.service.api.user.UserService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.MsgController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.dbumama.market.web.core.utils.IpKit;
import com.jfinal.log.Log;
import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.api.ApiConfigKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.CustomServiceApi;
import com.weixin.sdk.api.UserApi;
import com.weixin.sdk.kit.MsgEncryptKit;
import com.weixin.sdk.kit.WxSdkPropKit;
import com.weixin.sdk.msg.OutMsgXmlBuilder;
import com.weixin.sdk.msg.in.InImageMsg;
import com.weixin.sdk.msg.in.InLinkMsg;
import com.weixin.sdk.msg.in.InLocationMsg;
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
import com.weixin.sdk.msg.out.OutCustomMsg;
import com.weixin.sdk.msg.out.OutMsg;
import com.weixin.sdk.msg.out.OutNewsMsg;
import com.weixin.sdk.msg.out.OutTextMsg;

/**
 * 并设置好weixin开发者中心的 URL 与 token ，使 URL 指向该
 * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
@RouteBind(path="wx/message")
public class WeixinMsgController extends MsgController { 

	@BY_NAME
	private UserService userService;
	
    static Log logger = Log.getLog(WeixinMsgController.class);
    
//    private static final String helpStr = "\t发送 help 可获得帮助，发送\"视频\" 可获取视频教程，发送 \"美女\" 可看美女，发送 music 可听音乐 ，发送新闻可看JFinal新版本消息。公众号功能持续完善中";

    /**
     * 如果要支持多公众账号，只需要在此返回各个公众号对应的  ApiConfig 对象即可
     * 可以通过在请求 url 中挂参数来动态从数据库中获取 ApiConfig 属性值
     */
    public ApiConfig getApiConfig() {
        ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken(WxSdkPropKit.get("token"));
        ac.setAppId(WxSdkPropKit.get("wx_app_id"));
        ac.setAppSecret(WxSdkPropKit.get("wx_app_secret"));

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(WxSdkPropKit.getBoolean("encryptMessage", true));
        ac.setEncodingAesKey(WxSdkPropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

	protected void processInTextMsg(InTextMsg inTextMsg) {
    	logger.debug("处理文本消息,消息内容:" + inTextMsg.getContent());
		if("1".equals(inTextMsg.getContent())){
			ApiResult apiResult = CustomServiceApi.getKfOnlineList();
			@SuppressWarnings("rawtypes")
			List kfList = apiResult.getList("kf_online_list");
			if(kfList == null || kfList.size()<=0){
				OutTextMsg outMsg = new OutTextMsg(inTextMsg);
				outMsg.setContent("抱歉，没有人工客服在线！");
				render(outMsg);
			}else{
				//转发到多客服系统（随机转发到多客服）
				OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
    	        render(outCustomMsg);
			}
		}else{
			OutTextMsg outMsg = new OutTextMsg(inTextMsg);
			outMsg.setContent("欢迎您!有问题找客服，回复“1”联系人工客服");
			render(outMsg);
		}
    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
    	OutNewsMsg outMsg = new OutNewsMsg(inVoiceMsg);
		outMsg.addNews("欢迎您!", "", "https://mmbiz.qlogo.cn/mmbiz/sVVFr4mKVhCvdwXSfwT3ibgp26NRugJCbMYYjSgBeCVdTKNOHpVozCENubxL152SUd4tSA8hrW5EIiamyWUDZdtg/0?wx_fmt=jpeg", WxSdkPropKit.get("wx_domain"));
		render(outMsg);
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVoiceMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInVideoMsg(InVideoMsg inVideoMsg) {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inVideoMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInShortVideoMsg(InShortVideoMsg inShortVideoMsg) {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inShortVideoMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInLocationMsg(InLocationMsg inLocationMsg) {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLocationMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInLinkMsg(InLinkMsg inLinkMsg) {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inLinkMsg);
        render(outCustomMsg);
    }

    @Override
    protected void processInCustomEvent(InCustomEvent inCustomEvent) {
        logger.debug("测试方法：processInCustomEvent()");
        renderNull();
    }

    protected void processInImageMsg(InImageMsg inImageMsg) {
        //转发给多客服PC客户端
        OutCustomMsg outCustomMsg = new OutCustomMsg(inImageMsg);
        render(outCustomMsg);
    }

    /**
     * 实现父类抽方法，处理关注/取消关注消息
     */
    protected void processInFollowEvent(InFollowEvent inFollowEvent) {
		/*logger.info("取消关注：fromUserName[" + inFollowEvent.getFromUserName() + "], "
				+ "toUserName[" + inFollowEvent.getToUserName() + "],"
				+ "msgType[" + inFollowEvent.getMsgType() + "]");*/
        if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
        	//获取用户信息
    		ApiResult userInfo = UserApi.getUserInfo(inFollowEvent.getFromUserName());
        	userService.saveOrUpdate(WxSdkPropKit.getLong("seller_id"), inFollowEvent.getFromUserName(), userInfo.getJson(), IpKit.getRealIp(getRequest()));
    		OutNewsMsg outMsg = new OutNewsMsg(inFollowEvent);
    		outMsg.addNews("欢迎您!", "", "https://mmbiz.qlogo.cn/mmbiz/sVVFr4mKVhCvdwXSfwT3ibgp26NRugJCbMYYjSgBeCVdTKNOHpVozCENubxL152SUd4tSA8hrW5EIiamyWUDZdtg/0?wx_fmt=jpeg", WxSdkPropKit.get("wx_domain"));
    		render(outMsg);
        }else if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
    		userService.cancelSubscribe(inFollowEvent.getFromUserName());
    		renderNull();
        }
    }
    
    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
    	renderNull();
    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInMassEvent(InMassEvent inMassEvent) {
        logger.debug("测试方法：processInMassEvent()");
        renderNull();
    }

    /**
     * 实现父类抽方法，处理自定义菜单事件
     */
    protected void processInMenuEvent(InMenuEvent inMenuEvent) {
        logger.debug("菜单事件：" + inMenuEvent.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
        logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
        logger.debug("测试方法：processInTemplateMsgEvent()");
        renderNull();
    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
        logger.debug("摇一摇周边设备信息通知事件：" + inShakearoundUserShakeEvent.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
        logger.debug("资质认证成功通知事件：" + inVerifySuccessEvent.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
        logger.debug("资质认证失败通知事件：" + inVerifyFailEvent.getFromUserName());
        renderNull();
    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {
    	renderNull();
    }
    
    public void render(OutMsg outMsg) {
		String outMsgXml = OutMsgXmlBuilder.build(outMsg);
		// 开发模式向控制台输出即将发送的 OutMsg 消息的 xml 内容
		if (ApiConfigKit.isDevMode()) {
			System.out.println("发送消息:");
			System.out.println(outMsgXml);
			System.out.println("--------------------------------------------------------------------------------\n");
		}
		logger.debug("生成内容(未加密)：" + outMsgXml);
		// 是否需要加密消息
		if (ApiConfigKit.getApiConfig().isEncryptMessage()) {
			outMsgXml = MsgEncryptKit.encrypt(outMsgXml, getPara("timestamp"), getPara("nonce"));
		}
		//logger.debug("生成内容：" + outMsgXml);
		renderText(outMsgXml, "text/xml");
	}

	@Override
	protected void processInCardPasscheckEvent(InCardPasscheckEvent inCardPasscheckEvent) {
		/*AuthUser authUser = AuthUser.dao.findFirst("select * from " + AuthUser.table + " where app_id=? ", getAuthAppId());
		if(authUser != null){
			ApiResult cardResult = CompCardApi.getCard(getAccecssToken(authUser.getAppId()), authUser.getAppId(), JsonKit.toJson(ParaMap.create("card_id", inCardPasscheckEvent.getCardId()).getData()));
			if(cardResult != null && cardResult.isSucceed()){
				try {
					cardService.save(authUser.getSellerId(), authUser.getId(), inCardPasscheckEvent.getCardId(), cardResult.getJson());	
				} catch (CardException e) {
					logger.error(e.getMessage());
				}
			}
		}*/
		OutTextMsg outMsg = new OutTextMsg(inCardPasscheckEvent);
        outMsg.setContent("会员卡["+inCardPasscheckEvent.getCardId() + "]审核通过");
        render(outMsg);		
	}

	@Override
	protected void processInUserGetcardEvent(InUserGetcardEvent inUserGetcardEvent) {
		/*OutTextMsg outMsg = new OutTextMsg(inUserGetcardEvent);
        outMsg.setContent("会员卡["+inUserGetcardEvent.getCardId() + "]被领取" + ",code:" + inUserGetcardEvent.getUserCardCode());
        render(outMsg);*/
		renderNull();
	}
	
	@Override
	protected void processInUserDelcardEvent(InUserDelcardEvent inUserDelcardEvent) {
		/*OutTextMsg outMsg = new OutTextMsg(inUserDelcardEvent);
        outMsg.setContent("会员卡["+inUserDelcardEvent.getCardId() + "]被删除");
        render(outMsg);*/
		/*BuyerCard buyerCard = cardService.getUserBuyerCard(inUserDelcardEvent.getFromUserName(), inUserDelcardEvent.getCardId(), inUserDelcardEvent.getUserCardCode());
		if(buyerCard != null) buyerCard.delete();*///删除用户领卡记录
		renderNull();
	}

	@Override
	protected void processInUpdateMembercardEvent(InUpdateMembercardEvent inUpdateMembercardEvent) {
		OutTextMsg outMsg = new OutTextMsg(inUpdateMembercardEvent);
        outMsg.setContent(inUpdateMembercardEvent.getUserCardCode() + ",会员卡["+inUpdateMembercardEvent.getCardId() + "]更新");
        render(outMsg);		
	}

	@Override
	protected void processInCardPayorderEvent(InCardPayorderEvent inCardPayorderEvent) {
		OutTextMsg outMsg = new OutTextMsg(inCardPayorderEvent);
        outMsg.setContent("事件processInCardPayorderEvent,orderId:" + inCardPayorderEvent.getOrderId());
        render(outMsg);	
	}

	@Override
	protected void processInCardskuRemindEvent(InCardskuRemindEvent event) {
		OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("事件processInCardskuRemindEvent，cardId:" + event.getCardId());
        render(outMsg);	
	}

	@Override
	protected void processInUserConsumecardEvent(InUserConsumecardEvent event) {
		OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("事件processInUserConsumecardEvent，cardId:" + event.getCardId());
        render(outMsg);			
	}

	@Override
	protected void processInUserEntersessionFromcardEvent(InUserEntersessionFromcardEvent event) {
		/*OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("事件processInUserEntersessionFromcardEvent，cardId:" + event.getCardId());
        render(outMsg);*/
		renderNull();
	}

	@Override
	protected void processInUserViewcardEvent(InUserViewcardEvent event) {
		/*OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("事件processInUserViewcardEvent，cardId:" + event.getCardId());
        render(outMsg);*/
		renderNull();
	}

	@Override
	protected void processInUserPayfromPaycellEvent(InUserPayfromPaycellEvent event) {
		OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("事件processInUserPayfromPaycellEvent，cardId:" + event.getCardId());
        render(outMsg);			
	}

	/**
	 * 会员卡激活事件
	 */
	@Override
	protected void processInSubmitMembercardUserinfoEvent(InSubmitMembercardUserinfoEvent event) {
		/*OutTextMsg outMsg = new OutTextMsg(event);
		outMsg.setContent("会员卡:cardId:" + event.getCardId() + "被激活;激活code:" + event.getUserCardCode());
        render(outMsg);*/
		renderNull();
	}

	/**
	 * 小程序审核成功事件
	 */
	@Override
	protected void processInWeappAduitSuccessEvent(InWeappAuditSuccessEvent event) {
		logger.info("小程序app id：" + event.getToUserName());
		renderNull();
	}

	/**
	 * 小程序审核失败事件
	 */
	@Override
	protected void processInWeappAduitFailEvent(InWeappAuditFailEvent event) {
		logger.info("小程序app id：" + event.getToUserName());
		renderNull();
	}
	
}