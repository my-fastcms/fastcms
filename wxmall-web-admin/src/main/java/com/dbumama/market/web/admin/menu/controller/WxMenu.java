package com.dbumama.market.web.admin.menu.controller;

import com.weixin.sdk.api.ApiConfig;
import com.weixin.sdk.api.ApiConfigKit;
import com.weixin.sdk.api.ApiResult;
import com.weixin.sdk.api.MenuApi;

public class WxMenu {

	public void createWxMenu(){
    	ApiConfig ac = new ApiConfig();

        // 配置微信 API 相关常量
        ac.setToken("__my__nettoken__2016");
        ac.setAppId("wxffdd5a3f3bd46c78");
        ac.setAppSecret("81967b09d1c72b3c8fbc0c294166b201");

        /**
         *  是否对消息进行加密，对应于微信平台的消息加解密方式：
         *  1：true进行加密且必须配置 encodingAesKey
         *  2：false采用明文模式，同时也支持混合模式
         */
        ac.setEncryptMessage(true);
        ac.setEncodingAesKey("EpSqUcAzCKOHbm3LxaJgaaQpJIrFeSTgaxakbl7lspy");
        
        ApiConfigKit.setThreadLocalApiConfig(ac);
        
        String menuJsonStr = "{" + 
		     "\"button\":[" + 
		     "{" +	
		     "     \"name\":\"微信商城\"," + 
		     " 		\"type\":\"view\"," +   
		     "   	\"url\":\"http://mz.dbumama.com/\"" +
		     "}," + 
		      "{" + 
		          " \"name\":\"个人中心\"," +
		          " \"sub_button\":[" + 
		          "{" + 	
		           " \"type\":\"view\"," +   
		            "   \"name\":\"个人中心\"," +
		            "   \"url\":\"http://mz.dbumama.com/user\"" +
		            "}," + 
		            "{" +
		            "   \"type\":\"view\"," +
		            "   \"name\":\"购物车\"," +
		            "   \"url\":\"http://mz.dbumama.com/cart\"" +
		            "}]" + 
		       "}," + 
		       "{" + 
		          " \"name\":\"XXX\"," +
		          " \"sub_button\":[" + 
		          "{" + 	
		           " \"type\":\"click\"," +   
		            "   \"name\":\"公司介绍\"," +
		            "   \"key\":\"MENU_COMP_INFO\"" +
		            "}," + 
		            /*"{" + 	
			           " \"type\":\"view\"," +   
			            "   \"name\":\"视频介绍\"," +
			            "   \"url\":\"http://mz.dbumama.com/help\"" +
			        "}," +*/
		            "{" +
		            "   \"type\":\"click\"," +
		            "   \"name\":\"客服咨询\"," +
		            "   \"key\":\"MENU_CUSTOM_SERVICE\"" +	
		            "}]" + 
		       "}" +
		    "]" +
		 "}";
        
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
