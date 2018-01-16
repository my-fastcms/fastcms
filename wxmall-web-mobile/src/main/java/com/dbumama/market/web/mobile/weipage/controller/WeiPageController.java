package com.dbumama.market.web.mobile.weipage.controller;

import com.dbumama.market.model.WeiPage;
import com.dbumama.market.service.api.ump.UmpException;
import com.dbumama.market.service.api.weipage.WeiPageService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
@RouteBind(path = "feature")
public class WeiPageController extends BaseMobileController{
	@BY_NAME
	private WeiPageService weiPageService; 
	
	public void show(){
		Long id = getParaToLong("id");
		WeiPage entity=weiPageService.getWeiPageInfoById(id);
		setAttr("entity", entity);
		setAttr("url", "show?id="+entity.getId());
		render("/feature/index.html");
	}
	
	public void getPreviewHtml(){
		Long id = getParaToLong("id");
		
		WeiPage entity=weiPageService.getWeiPageById(id);
		if(entity.getSellerId().longValue()!=getSellerId().longValue()){
			throw new UmpException("非法操作！");
		}
		try{
			String html=weiPageService.getPageHtml(entity,true);
			rendSuccessJson(html); 
		}catch(Exception ex){
			rendFailedJson("生成失败！");
			ex.printStackTrace();
		}
		
	}
}
