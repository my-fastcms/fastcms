package com.dbumama.market.web.core.handler;

import java.util.Date;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dbumama.market.service.api.authuser.AuthUserConfigService;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;
import com.weixin.sdk.api.JsTicket;
import com.weixin.sdk.api.JsTicketApi;
import com.weixin.sdk.api.JsTicketApi.JsApiType;
import com.weixin.sdk.utils.SignKit;

/**
 * 准备调用微信JS SDK接口需要的参数
 * wjun_java@163.com
 * 2015年12月15日
 */
@Before(IocInterceptor.class)
public class WeixinJssdkHandler extends Handler {
	
	public Logger log = Logger.getLogger(getClass());
	
	@BY_NAME
	private AuthUserConfigService authUserConfigService;
	
	/**
	 * 需要准备分享参数的url
	 */
	final static String inclusions [] = {"/lottery/draw/", "/product/detail/", "/product/join/"};
	
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
			String nonceStr = SignKit.genRandomString32();
			if(authUserConfigService.getAuthConfig() != null){
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
					request.setAttribute("appId", getAppId(request));
					request.setAttribute("requestUrl", rurl);
				}
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
	
	protected String getAppId(HttpServletRequest request){
		if(JFinal.me().getConstants().getDevMode()){
			return PropKit.get("auth_app_id");
		}
		String serverName = request.getServerName();
		serverName = serverName.substring(0, serverName.indexOf("."));
		return serverName;
	}

}
