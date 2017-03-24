package com.dbumama.market.web.admin.menu.controller;

import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.api.ApiConfigKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.MenuApi;

public class WxMenu {

	public void createWxMenu(){
    	ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken("");
        ac.setAppId("");
        ac.setAppSecret("");

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(true);
        ac.setEncodingAesKey("");
        
        ApiConfigKit.setThreadLocalApiConfig(ac);
        
        String menuJsonStr = "";
        
        ApiResult result = MenuApi.createMenu(menuJsonStr);
        if(result.isSucceed()){
        	System.out.println("菜单创建成功");
        }
        
        ApiConfigKit.removeThreadLocalApiConfig();
    }
	
	public static void main(String[] args) {
		WxMenu wxMenu = new WxMenu();
		wxMenu.createWxMenu();
	}
	
}
