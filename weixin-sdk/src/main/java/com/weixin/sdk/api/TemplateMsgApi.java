/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com.weixin.sdk.api;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.weixin.sdk.cache.IAccessTokenCache;
import com.weixin.sdk.utils.HttpUtils;

/**
 * 模板消息 API
 * 文档地址：http://mp.weixin.qq.com/wiki/17/304c1885ea66dbedf7dc170d84999a9d.html
 */
public class TemplateMsgApi {
	
	private static String sendApiUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	
	private static String getTemplateIdUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";
	
	static IAccessTokenCache accessTokenCache = ApiConfigKit.getAccessTokenCache();
	
	/**
	 * 发送模板消息
	 */
	public static ApiResult send(String jsonStr) {
		String jsonResult = HttpUtils.post(sendApiUrl + AccessTokenApi.getAccessToken().getAccessToken(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	public static String getTemplateId(String jsonStr){
		String templateId = accessTokenCache.get("template_msg_id");
		if(StringUtils.isBlank(templateId)){
			String jsonResult = HttpUtils.post(getTemplateIdUrl + AccessTokenApi.getAccessTokenStr(), jsonStr);
			ApiResult result = new ApiResult(jsonResult);
			templateId = result.getStr("template_id");
			accessTokenCache.set("template_msg_id", templateId);
		}
		return templateId;
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("template_id_short", "TM00785");
		String templateId = TemplateMsgApi.getTemplateId(json.toString());
		System.out.println("templateId1:" + templateId);
		
		TemplateData templateData = TemplateData.New().setTemplate_id(templateId)
			.setTouser("oQ774wnoZjqJt4UdAXusjT9WBvgI")
			.setUrl("http://m.dbumama.com/pay/luck")
			.add("first", "恭喜好农民中奖了", "#173177")
			.add("program", "疯狂抽奖", "#173177")
			.add("result", "抽中现金红包10元", "#173177")
			.add("remark", "疯狂抽奖，公平公正，点击去看看", "#173177");
		
		ApiResult apiResult = TemplateMsgApi.send(templateData.build());
		
		if(!apiResult.isSucceed()){
			System.out.println("error_code:" + apiResult.getErrorCode() + ",error_msg" + apiResult.getErrorMsg());
		}else{
			System.out.println("sucess:" + apiResult.isSucceed());
		}
		
		for(int i=0;i<30;i++){
			templateId = TemplateMsgApi.getTemplateId(json.toString());
			System.out.println("templateId"+i+":" + templateId);
			templateData = TemplateData.New().setTemplate_id(templateId)
					.setTouser("oQ774wnoZjqJt4UdAXusjT9WBvgI")
					.setUrl("http://m.dbumama.com/pay/luck")
					.add("first", "恭喜好农民中奖了", "#173177")
					.add("program", "疯狂抽奖", "#173177")
					.add("result", "抽中现金红包10元", "#173177")
					.add("remark", "疯狂抽奖，公平公正，点击去看看", "#173177");
			
			TemplateMsgApi.send(templateData.build());
			if(!apiResult.isSucceed()){
				System.out.println("error_code:" + apiResult.getErrorCode() + ",error_msg" + apiResult.getErrorMsg());
			}else{
				System.out.println("sucess:" + apiResult.isSucceed());
			}
		}
		
	}
	
}


