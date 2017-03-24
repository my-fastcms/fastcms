package com.dbumama.market.web.admin.weixin.controller;

import java.util.Date;
import java.util.List;

import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.utils.CommonUtil;
import com.dbumama.market.web.core.utils.IpKit;
import com.dbumama.market.web.core.weixin.MsgController;
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
import com.weixin.sdk.msg.in.speech_recognition.InSpeechRecognitionResults;
import com.weixin.sdk.msg.out.OutCustomMsg;
import com.weixin.sdk.msg.out.OutMsg;
import com.weixin.sdk.msg.out.OutNewsMsg;
import com.weixin.sdk.msg.out.OutTextMsg;

/**
 * 将此 DemoController 在YourJFinalConfig 中注册路由，
 * 并设置好weixin开发者中心的 URL 与 token ，使 URL 指向该
 * DemoController 继承自父类 WeixinController 的 index
 * 方法即可直接运行看效果，在此基础之上修改相关的方法即可进行实际项目开发
 */
@RouteBind(path = "/weixin/msg")
public class WeixinMsgController extends MsgController { 

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
        ac.setEncryptMessage(WxSdkPropKit.getBoolean("encryptMessage", false));
        ac.setEncodingAesKey(WxSdkPropKit.get("encodingAesKey", "setting it in config file"));
        return ac;
    }

	protected void processInTextMsg(InTextMsg inTextMsg) {
        //转发给多客服PC客户端
/*        OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
        render(outCustomMsg);*/
    	logger.debug("处理文本消息,消息内容:" + inTextMsg.getContent());
    	
    	//获取是否处理客服状态
    	if("1".equals(inTextMsg.getContent())){
    		logger.debug("转发给多客服");
    		
    		ApiResult apiResult = CustomServiceApi.getKfOnlineList();
    		
    		@SuppressWarnings("rawtypes")
			List kfList = apiResult.getList("kf_online_list");
			if(kfList == null || kfList.size()<=0){
				OutTextMsg outMsg = new OutTextMsg(inTextMsg);
				outMsg.setContent("抱歉，当前没有人工客服在线！");
				render(outMsg);
			}else{
				//转发到多客服系统（随机转发到多客服）
				OutCustomMsg outCustomMsg = new OutCustomMsg(inTextMsg);
    	        render(outCustomMsg);
			}
    	}
    	else{
	    	OutNewsMsg outMsg = new OutNewsMsg(inTextMsg);
			outMsg.addNews("XXX商城欢迎您!", "欢迎你来购物^_^，有问题请咨询客服，回复“1”将转接到人工客服", "", "");
			render(outMsg);
    	}
    }

    @Override
    protected void processInVoiceMsg(InVoiceMsg inVoiceMsg) {
    	OutNewsMsg outMsg = new OutNewsMsg(inVoiceMsg);
		outMsg.addNews("XXX商城欢迎您!", "欢迎你来购物^_^", "", "");
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
        if (InFollowEvent.EVENT_INFOLLOW_SUBSCRIBE.equals(inFollowEvent.getEvent())) {
        	//获取用户信息
        	logger.debug("关注：" + inFollowEvent.getFromUserName());
        	saveOrUpdateMember(inFollowEvent.getFromUserName(), true);
    		OutNewsMsg outMsg = new OutNewsMsg(inFollowEvent);
    		outMsg.addNews("XXX商城欢迎您!", "欢迎您来购物^_^", "", "");
    		render(outMsg);
        }else if (InFollowEvent.EVENT_INFOLLOW_UNSUBSCRIBE.equals(inFollowEvent.getEvent())) {
            logger.debug("取消关注：" + inFollowEvent.getFromUserName());
            OutTextMsg outMsg = new OutTextMsg(inFollowEvent);
            outMsg.setContent("[" + inFollowEvent.getFromUserName() + "]取消了关注");
            render(outMsg);
        }
    }
    
    private void saveOrUpdateMember(String openId, boolean flag){
    	ApiResult userInfo = UserApi.getUserInfo(openId);
    	BuyerUser member = BuyerUser.dao.findFirst("select * from t_buyer_user where open_id=? ", openId);
        if (member == null) {
        	AuthUser authUser = AuthUser.dao.findFirst("select * from " + AuthUser.table + " where is_used=1 and active=1");
        	member = new BuyerUser();
            member.set("created", new Date())
            .set("updated", new Date())
            .set("active", 1)
            .set("seller_id", authUser.getSellerId())
            .set("auth_app_id", authUser.getAppId())
            .set("email", "")
            .set("open_id", openId)
            .set("last_login_time", new Date())
            .set("access_ip", IpKit.getRealIpV2(getRequest()))
            .set("password", "")
            .set("nickname", CommonUtil.filterEmoji(userInfo.getStr("nickname")))
            .set("sex", userInfo.getInt("sex"))
            .set("score", 0)
			.set("city", userInfo.getStr("city"))
			.set("country", userInfo.getStr("country"))
			.set("province", userInfo.getStr("province"))
			.set("language", userInfo.getStr("language"))
			.set("headimgurl", userInfo.getStr("headimgurl"))
			.set("subscribe", userInfo.getInt("subscribe") == null ? 0 :userInfo.getInt("subscribe"))
			.set("subscribe_time", userInfo.getLong("subscribe_time") == null ? new Date() : new Date(userInfo.getLong("subscribe_time")))
			.set("unionid", userInfo.getStr("unionid"))
			.set("remark", userInfo.getStr("remark"))
			.set("groupid", userInfo.getInt("groupid"));
            member.save();
        } else {
        	//更新用户名和用户头像
        	if(flag){//取消关注的时候，不做头像跟昵称更新
        		if(member.getStr("nickname") == null || !member.getStr("nickname").equals(userInfo.getStr("nickname"))){
            		member.set("nickname", CommonUtil.filterEmoji(userInfo.getStr("nickname")));
            	}
            	if(member.getStr("headimgurl") == null || !member.getStr("headimgurl").equals(userInfo.getStr("headimgurl"))){
            		member.set("headimgurl", userInfo.getStr("headimgurl"));
            	}	
        	}
        	
        	member.set("subscribe", userInfo.getInt("subscribe") == null ? 0 :userInfo.getInt("subscribe"));
        	if(member.getInt("subscribe") ==null || userInfo.getInt("subscribe")==0){
        		member.setActive(0);
        	}else{
        		member.setActive(1);
        	}
        	member.set("subscribe_time", userInfo.getLong("subscribe_time") == null ? new Date() : new Date(userInfo.getLong("subscribe_time")));
            member.set("access_ip", IpKit.getRealIpV2(getRequest()));
            member.set("updated", new Date());
            member.set("last_login_time", new Date());
            member.update();
        }
    }

    @Override
    protected void processInQrCodeEvent(InQrCodeEvent inQrCodeEvent) {
    	saveOrUpdateMember(inQrCodeEvent.getFromUserName(), true);
    	OutNewsMsg outMsg = new OutNewsMsg(inQrCodeEvent);
		outMsg.addNews("XXX商城欢迎您!", "欢迎您来购物^_^", "", "");
		render(outMsg);
    }

    @Override
    protected void processInLocationEvent(InLocationEvent inLocationEvent) {
        logger.debug("发送地理位置事件：" + inLocationEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inLocationEvent);
        outMsg.setContent("地理位置是：" + inLocationEvent.getLatitude());
        render(outMsg);
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
        OutTextMsg outMsg = new OutTextMsg(inMenuEvent);
        String menuKey = inMenuEvent.getEventKey();
        //如果是点击"客户服务"菜单 
        if("MENU_CUSTOM_SERVICE".equals(menuKey)){
        	 String strKf = "您好，您可以通过在线客服功能在公众号向我们的客服人员咨询相关问题。\n"
             		+ "人工服务请回复[1]\n";
        	 this.getSession().setAttribute("KF_STATUS", "YES");
             outMsg.setContent(strKf);
             render(outMsg);        	
        } else if("MENU_COMP_INFO".equals(menuKey)){
        	OutNewsMsg outNewMsg = new OutNewsMsg(inMenuEvent);
        	outNewMsg.addNews("XXX商城欢迎您!", "欢迎您来购物^_^", 
        			"", 
        			"");
    		render(outNewMsg);
        }
        else if("MENU_SHARE_FREND".equals(menuKey)){  //如果点击呼唤好友菜单 
        	ApiResult apiResult = UserApi.getUserInfo(inMenuEvent.getFromUserName());
    		String openId = apiResult.getStr("openid");
    		StringBuffer urlBuffer = new StringBuffer().append(this.getRequest().getContextPath())
    				.append("/weixin/user/qrcode?openId=").append(openId);
    		logger.debug("转发到url：" + urlBuffer.toString());
        	this.redirect(urlBuffer.toString());
        }else {
        	renderNull();
        }
    }

    @Override
    protected void processInSpeechRecognitionResults(InSpeechRecognitionResults inSpeechRecognitionResults) {
        logger.debug("语音识别事件：" + inSpeechRecognitionResults.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inSpeechRecognitionResults);
        outMsg.setContent("语音识别内容是：" + inSpeechRecognitionResults.getRecognition());
        render(outMsg);
    }

    @Override
    protected void processInTemplateMsgEvent(InTemplateMsgEvent inTemplateMsgEvent) {
        logger.debug("测试方法：processInTemplateMsgEvent()");
        renderNull();
    }

    @Override
    protected void processInShakearoundUserShakeEvent(InShakearoundUserShakeEvent inShakearoundUserShakeEvent) {
        logger.debug("摇一摇周边设备信息通知事件：" + inShakearoundUserShakeEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inShakearoundUserShakeEvent);
        outMsg.setContent("摇一摇周边设备信息通知事件UUID：" + inShakearoundUserShakeEvent.getUuid());
        render(outMsg);
    }

    @Override
    protected void processInVerifySuccessEvent(InVerifySuccessEvent inVerifySuccessEvent) {
        logger.debug("资质认证成功通知事件：" + inVerifySuccessEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inVerifySuccessEvent);
        outMsg.setContent("资质认证成功通知事件：" + inVerifySuccessEvent.getExpiredTime());
        render(outMsg);
    }

    @Override
    protected void processInVerifyFailEvent(InVerifyFailEvent inVerifyFailEvent) {
        logger.debug("资质认证失败通知事件：" + inVerifyFailEvent.getFromUserName());
        OutTextMsg outMsg = new OutTextMsg(inVerifyFailEvent);
        outMsg.setContent("资质认证失败通知事件：" + inVerifyFailEvent.getFailReason());
        render(outMsg);
    }

    @Override
    protected void processInPoiCheckNotifyEvent(InPoiCheckNotifyEvent inPoiCheckNotifyEvent) {

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
}






