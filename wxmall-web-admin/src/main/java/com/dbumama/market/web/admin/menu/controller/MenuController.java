package com.dbumama.market.web.admin.menu.controller;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dbumama.market.model.AuthUser;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.ehcache.CacheKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.MenuApi;

@RouteBind(path="menu")
public class MenuController extends BaseController{
	private static final String CACHENAME = "/wxappmenu/list";
	private static final String CACHENAME_KEY = "key";
	public void index(){
		List<AuthUser> authUsers = AuthUser.dao.find("select * from " + AuthUser.table + " where seller_id=?", getSellerId());
		setAttr("authUsers", authUsers);
		render("m_index.html");
	}
	
	public void list(){
		//获取公众号已设置的菜单列表
		if(getUsedAuthUser() == null || StrKit.isBlank(getUsedAuthUser().getAppId())){
			rendFailedJson("没有绑定公众账号");
			return;
		}
		
		String authAppId = getUsedAuthUser().getAppId();
		ApiResult apiResult = CacheKit.get(CACHENAME, CACHENAME_KEY + authAppId);
		if(apiResult == null){
			apiResult = MenuApi.getMenu();
			
			if(apiResult.isAccessTokenInvalid()){
				rendFailedJson("请重新绑定公众号");
				return;
			}
			if(!apiResult.isSucceed()){
				rendFailedJson("调用获取菜单接口失败");
				return;
			}
			CacheKit.put(CACHENAME, CACHENAME_KEY + authAppId, apiResult);
		}
		
		rendSuccessJson(apiResult);
	}
	
	public void save(){
		if(getUsedAuthUser() == null || StrKit.isBlank(getUsedAuthUser().getAppId())){
			rendFailedJson("没有绑定公众账号");
			return;
		}
		
		String authAppId = getUsedAuthUser().getAppId();
		if(StrKit.isBlank(authAppId)){
			rendFailedJson("请选择公众账号");
			return;
		}
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
		CacheKit.remove(CACHENAME, CACHENAME_KEY + authAppId);
		rendSuccessJson();
	}
	
	public void modules(){
		render("m_select_modules.html");
	}
	
}
