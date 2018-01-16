/**
 * 文件名:BaseController.java
 * 版本信息:1.0
 * 日期:2015-5-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.core.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.service.constants.Constants;
import com.dbumama.market.service.utils.ResultUtil;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.render.JsonRender;
import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-9
 */
@Before({IocInterceptor.class})
public abstract class BaseMobileController extends ApiController {
	
	public static final String ORDER_SESSION_KEY = "orders"; //购物车临时订单数据
	protected static final String CACHENAME_COMP_TIKET = "comp_tiket_cache";
	protected static final String CACHENAME_COMP_TIKET_KEY = "key_comp_tiket";
	
	public Logger log = Logger.getLogger(getClass());

	protected final static String SYSTEM_ERROR_MSG = "系统错误";
	
	//用于金额保留两位小数
    public static final DecimalFormat df = new DecimalFormat("#.00");
    
	@Override
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

	protected int getPageNo(){
		return this.getParaToInt("page", 1);
	}
	
	protected int getPageSize(){
		return this.getParaToInt("rows", 10);
	}
	
	public Long getSellerId(){
		return getBuyerUser()==null ? null : getBuyerUser().getSellerId();
	}
	
	protected BuyerUser getBuyerUser(){
		return (BuyerUser)getSession().getAttribute(Constants.BUYER_USER_IN_SESSION);
	}
	
	protected Long getBuyerId(){
		return getBuyerUser().getId();
	}
	
	/*public SellerUser getSellerUser(){
		return SellerUser.dao.findById(getBuyerUser().getSellerId());
	}*/

	protected String getOpenId(){
		return getBuyerUser().getOpenId();
	}
	
	/**
	 * HTML视图
	 * @param view 视图文件名不含.html
	 */
	protected void renderHTML(String view) {
		if(view.endsWith(".html")){
			super.render(view);
		}else{
			super.render(view+".html");
		}
	}

	protected void rendSuccessJson(Object data){
		rendJson(ResultUtil.genSuccessResult(data));
	}
	
	protected void rendSuccessJson(){
		rendJson(ResultUtil.genSuccessResult());
	}
	
	protected void rendFailedJson(final String msg){
		rendJson(ResultUtil.genFailedResult(msg));
	}
	
	protected void rendFailedJson(final int code, final String msg){
		rendJson(ResultUtil.genFailedResult(code, msg));
	}
	
	protected void rendFailedJson(final String code, final String msg){
		rendJson(ResultUtil.genFailedResult(code, msg));
	}
	
	protected void rendJson(Object json){
		String agent = getRequest().getHeader("User-Agent");
		if(agent.contains("MSIE"))
			this.render(new JsonRender(json).forIE());
		else{
			this.render(new JsonRender(json));
		}
	}
	
	protected synchronized String getUUIDStr(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	protected String inputStream2String(InputStream in) throws UnsupportedEncodingException, IOException{
		if(in == null)
			return "";
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n, "UTF-8"));
		}
		return out.toString();
	}
	
	protected String getAppId(){
		if(JFinal.me().getConstants().getDevMode()){
			return PropKit.get("auth_app_id");
		}
		String serverName = getRequest().getServerName();
		serverName = serverName.substring(0, serverName.indexOf("."));
		return serverName;
	}
	
}
