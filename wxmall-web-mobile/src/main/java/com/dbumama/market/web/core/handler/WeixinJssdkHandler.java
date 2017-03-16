package com.dbumama.market.web.core.handler;

import java.util.Date;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jfinal.handler.Handler;
import com.weixin.sdk.api.JsTicket;
import com.weixin.sdk.api.JsTicketApi;
import com.weixin.sdk.api.JsTicketApi.JsApiType;
import com.weixin.sdk.kit.WxSdkPropKit;
import com.weixin.sdk.utils.SignKit;

/**
 * 准备调用微信JS SDK接口需要的参数
 * wjun_java@163.com
 * 2015年12月15日
 */
public class WeixinJssdkHandler extends Handler {
	
	public Logger log = Logger.getLogger(getClass());
	
	/**
	 * 需要准备分享参数的url
	 */
	final static String inclusions [] = {"/lottery/draw/", "/product/detail"};
	
	/* (non-Javadoc)
	 * @see com.jfinal.handler.Handler#handle(java.lang.String, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, boolean[])
	 */
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		if(check(target)){
			String mainUrl = "http://" + request.getServerName();
	        String rurl = mainUrl + request.getServletPath();
	        if (request.getQueryString() != null) {
	            rurl += "?" + request.getQueryString();
	        }
	        
	        /*ApiConfig ac = new ApiConfig();
            ac.setToken(WxSdkPropKit.get("token"));
            ac.setAppId(WxSdkPropKit.get("wx_app_id"));
            ac.setAppSecret(WxSdkPropKit.get("wx_app_secret"));
        	ApiConfigKit.setThreadLocalApiConfig(ac);*/
			String nonceStr = SignKit.genRandomString32();
			JsTicket compJsTicket = JsTicketApi.getTicket(JsApiType.jsapi);
			if(compJsTicket != null){
				String jsapiTicket = compJsTicket.getTicket();
				String timestamp = String.valueOf(new Date().getTime());

				// 准备调用支付js接口的参数
				TreeMap<String, Object> params = new TreeMap<String, Object>();
				params.put("jsapi_ticket", jsapiTicket);
				params.put("noncestr", nonceStr);
				params.put("timestamp", timestamp);
				params.put("url", rurl);
				
				request.setAttribute("signature", SignKit.signSHA1(params));
				request.setAttribute("nonceStr", nonceStr);
				request.setAttribute("timestamp", timestamp);
				request.setAttribute("appId", WxSdkPropKit.get("wx_app_id"));
				request.setAttribute("requestUrl", rurl);
			}
		}
		
		next.handle(target, request, response, isHandled);
	}
	
	boolean check(String target){
		for(String inclus : inclusions){
			if(inclus.equals(target) || inclus.contains(target)){
				return true;
			}
		}
		return false;
	}
	
}
