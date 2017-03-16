/**
 * 文件名:ItemsettingController.java
 * 版本信息:1.0
 * 日期:2015-7-10
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.setting.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.dbumama.market.model.AuthCert;
import com.dbumama.market.model.AuthUser;
import com.dbumama.market.service.utils.FileUtils;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.BaseController;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-7-10
 */
@RouteBind(path="setting")
public class SettingController extends BaseController{
	
	private static final String CACHENAME = "auth_user_cache";
	private static final String CACHENAME_KEY = "key";

	public void index(){
		render("st_index.html");
	}
	
	public void list(){
		String select = "select * ";
		String sqlExceptSelect = " from " + AuthUser.table + " a "
				+ "where a.seller_id=? and a.active = 1 ";
		Page<Record> pages = Db.paginate(getPageNo(), getPageSize(), select, sqlExceptSelect, getSellerId());
		rendSuccessJson(pages);
	}
	
	public void payconfig(){
		Long authUserId = getParaToLong(0);
		if(authUserId != null){
			AuthUser authUser = AuthUser.dao.findById(authUserId);
			setAttr("authUser", authUser);
		}
		render("st_pay_config.html");
	}
	
	public void setUsed(){
		Long authUserId = getParaToLong("authId");
		if(authUserId == null) {
			rendFailedJson("请传入authId");
			return;
		}
		AuthUser authUser = AuthUser.dao.findById(authUserId);
		if(authUser == null){
			rendFailedJson("公众账号不存在");
			return;
		}
		
		//有且只有一个正在使用的公众账号
		List<AuthUser> authUsers = AuthUser.dao.find("select * from " + AuthUser.table + " where seller_id=?", getSellerId());
		if(authUsers != null  && authUsers.size()>0){
			for(AuthUser _authUser : authUsers){
				_authUser.setIsUsed(false);
			}
			Db.batchUpdate(authUsers, authUsers.size());
		}
		
		authUser.setIsUsed(true);
		authUser.update();
		
		//清除auth user cache
		CacheKit.remove(CACHENAME, CACHENAME_KEY + "_" + getSellerId());
		
		rendSuccessJson();
		
	}
	
	//更新授权用户的支付配置信息
	public void update(){
		String filePath = "/" + getSellerId();
		List<UploadFile> uFile = getFiles(PropKit.get("upload_file_path") + filePath);
		
		Long authUserId = getParaToLong("id");
		String pay_mch_id = getPara("pay_mch_id");
		String pay_secret_key = getPara("pay_secret_key");

		if(authUserId == null || StrKit.isBlank(pay_mch_id) || StrKit.isBlank(pay_secret_key)){
			setAttr("error", "参数错误");
			forwardAction("/setting/payconfig/"+authUserId);
			return;
		}

		if(uFile == null || uFile.size()<=0){
			setAttr("error", "请上传文件");
			forwardAction("/setting/payconfig/"+authUserId);
			return;
		}
		
		AuthUser authUser = AuthUser.dao.findById(authUserId);
		if(authUser == null){
			setAttr("error", "授权用户不存在");
			forwardAction("/setting/payconfig/"+authUserId);
			return;
		}
			
		authUser.setPayMchId(pay_mch_id);
		authUser.setPaySecretKey(pay_secret_key);
		authUser.update();
		
		//是否有传证书文件
		AuthCert authCert = AuthCert.dao.findFirst("select * from " + AuthCert.table 
				+ " where app_id=? ", authUser.getAppId());
		if(authCert == null){
			authCert = new AuthCert();
			authCert.setCreated(new Date());
			authCert.setUpdated(new Date());
			authCert.setActive(1);
			authCert.setAppId(authUser.getAppId());
			try {
				authCert.setCertFile(FileUtils.toByteArray(uFile.get(0).getFile()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			authCert.save();
		}else{
			authCert.setUpdated(new Date());
			try {
				authCert.setCertFile(FileUtils.toByteArray(uFile.get(0).getFile()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			authCert.update();
		}
		//删除文件
		uFile.get(0).getFile().delete();
		redirect("/setting");
	}
	
}
