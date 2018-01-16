package com.weixin.sdk.shop;

import com.alibaba.fastjson.JSONObject;
import com.weixin.sdk.api.AccessTokenApi;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.kit.HttpKit;

/**
 * wjun_java@163.com
 * 2015年12月18日
 */
public class QueryItemByStatusApi {

	private static String getbystatus = "https://api.weixin.qq.com/merchant/getbystatus?access_token=";
	
	public static ApiResult getItemsByStatus(String jsonStr){
		String jsonResult = HttpKit.post(getbystatus + AccessTokenApi.getAccessToken().getAccessToken(), jsonStr);
		return new ApiResult(jsonResult);
	}
	
	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("status", "0");
		ApiResult result = QueryItemByStatusApi.getItemsByStatus(json.toString());
		System.out.println("=======json:" + result.getJson());
		/*List products = result.getList("products_info");
		System.out.println(products.size());*/
	}
	
}
