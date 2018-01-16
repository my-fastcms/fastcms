package com.dbumama.market.web.admin.menu.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.MenuApi;
import com.weixin.sdk.kit.WxSdkPropKit;

@RouteBind(path="menu")
public class MenuController extends BaseController{
	private static final String CACHENAME = "/wxappmenu/list";
	private static final String CACHENAME_KEY = "key";
	
	public void index(){
		render("m_index.html");
	}
	
	public void list(){
		ApiResult apiResult = CacheKit.get(CACHENAME, CACHENAME_KEY);
		if(apiResult == null){
			apiResult = MenuApi.getMenu();
			
			if(apiResult.isAccessTokenInvalid()){
				rendFailedJson("请重新绑定公众号");
				return;
			}
			
			if(apiResult.getErrorCode() != null && apiResult.getErrorCode() == 46003){
				//菜单不存在，创建菜单
				String menuJsonStr = "{" + 
					     "\"button\":[" + 
					     "{" +	
					     "     \"name\":\"微信商城\"," + 
					     " 		\"type\":\"view\"," +   
					     "   	\"url\":\""+WxSdkPropKit.get("wx_domain")+"/\"" +
					     "}," + 
					    "]" +
					 "}";
				ApiResult result = MenuApi.createMenu(menuJsonStr);
		        if(!result.isSucceed()){
		        	rendSuccessJson(result.getErrorMsg());
		        	return;
		        }
			}
			
			//再get一次
			apiResult = MenuApi.getMenu();
			
			if(!apiResult.isSucceed()){
				rendFailedJson("调用获取菜单接口失败");
				return;
			}
			CacheKit.put(CACHENAME, CACHENAME_KEY, apiResult);
		}
		
		rendSuccessJson(apiResult);
	}
	
	public void save(){
		/*if(!JFinal.me().getConstants().getDevMode()){
			rendFailedJson("体验账号不可操作");
			return;
		}*/
		
		String menus = getPara("menus");
		if(StrKit.isBlank(menus)){
			rendFailedJson("菜单数据不能为空");
			return;
		}
		
		JSONArray jsonArray = JSONArray.parseArray(menus);
		JSONObject postData = new JSONObject();
		postData.put("button", jsonArray);
		log.debug("json:" + postData.toJSONString());
		ApiResult result = MenuApi.createMenu(postData.toJSONString());
		if(result.isAccessTokenInvalid()){
			rendFailedJson("请重新绑定公众号");
			return;
		}
		if(!result.isSucceed()){
			rendFailedJson("调用更新菜单接口失败");
			return;
		}
		CacheKit.remove(CACHENAME, CACHENAME_KEY);
		rendSuccessJson();
	}
	
}
