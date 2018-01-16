/**
 * 文件名:ItemsettingController.java
 * 版本信息:1.0
 * 日期:2015-7-10
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.setting.controller;

import com.dbumama.market.model.AuthUserConfig;
import com.dbumama.market.model.Weapp;
import com.dbumama.market.service.api.authuser.AuthUserConfigService;
import com.dbumama.market.service.api.authuser.WeappService;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.upload.UploadFile;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-7-10
 */
@RouteBind(path="setting")
public class SettingController extends AdminBaseController<AuthUserConfig>{
	
	@BY_NAME
	private AuthUserConfigService authUserConfigService;
	@BY_NAME
	private WeappService weappService;
	
	public void index(){
		setAttr("authConfig", authUserConfigService.getAuthConfig());
		render("st_index.html");
	}
	
	public void xiaocx(){
		setAttr("weapp", weappService.getWeapp());
		render("st_pack_pub.html");
	}
	
	public void config(){
		setAttr("authConfig", authUserConfigService.getAuthConfig());
		render("st_auth_config.html");
	}
	
	//更新授权用户的支付配置信息
	public void save(){
		UploadFile uFile = getFile();

		try {
			authUserConfigService.save(getModel(), uFile == null ? null : uFile.getFile(), getSellerId());
			redirect("/setting");
		} catch (Exception e) {
			setAttr("error", e.getMessage());
			render("/setting/st_error.html");
		}
		
	}
	
	public void saveWapp(){
		try {
			Weapp weapp = (Weapp) getModelExt(Weapp.class);
			rendSuccessJson(weappService.save(weapp, getSellerId()));
		} catch (Exception e) {
			rendFailedJson(e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see com.dbumama.market.web.core.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<AuthUserConfig> getModelClass() {
		return AuthUserConfig.class;
	}
	
}
