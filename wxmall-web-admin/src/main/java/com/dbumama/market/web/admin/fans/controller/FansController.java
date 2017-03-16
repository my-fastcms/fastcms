/**
 * 文件名:FansController.java
 * 版本信息:1.0
 * 日期:2015-6-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.fans.controller;

import java.util.ArrayList;
import java.util.List;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.web.admin.fans.validator.BuyerUserValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-6-9
 */
@RouteBind(path="fans", viewPath="fans")
public class FansController extends AdminBaseController<BuyerUser>{

	static final String CACHENAME = "/fans/list";
	static final String CACHENAME_KEY = "fans_key";
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<BuyerUser> getModelClass() {
		return BuyerUser.class;
	}
	
	public void index(){
		render("fans.html");
	}
	
	public void list(){
		String qname = getPara("qname");
		
		List<Object> params = new ArrayList<Object>();
		params.add(getSellerId());
		params.add(getUsedAuthUser()==null? -1 : getUsedAuthUser().getAppId());
		StringBuilder sqlBuilder = new StringBuilder(" from "+BuyerUser.table + " where seller_id = ? and auth_app_id=? ");
		if(StrKit.notBlank(qname) && !"输入会员".equals(qname)){
			sqlBuilder.append(" and nickname like ?");
			params.add("%"+qname+"%");
		}
		if(StrKit.notBlank(getPara("active")) && getParaToInt("active")!=-1){
			sqlBuilder.append(" and active = ?");
			params.add(getPara("active"));
		}
		sqlBuilder.append(" order by created desc ");
		
		Object [] o = new Object[params.size()];
		for(int i=0;i<params.size();i++){
			o [i] = params.get(i); 
		}
		Page<BuyerUser> buyerUser = BuyerUser.dao.paginate(getPageNo(), getPageSize(), 
				"select * ", 
				sqlBuilder.toString(),
				o);
		rendSuccessJson(buyerUser);
	}
	
	public void detail(){
		Long id = getParaToLong("id");
		BuyerUser buser = BuyerUser.dao.findById(id);
		setAttr("fans", buser);
		render("fansdetail.html");
	}
	
	@Before(BuyerUserValidator.class)
	public void save(){
		CacheKit.removeAll(CACHENAME);
		BuyerUser user = null;
		try {
			user = getModel();
			if(user.getLong("id")!=null){
				user.update();
				rendSuccessJson("操作成功！将跳转到列表页面");	
			}
		} catch (Exception e) {
			e.printStackTrace();
			rendFailedJson("修改失败！");
		}
	}
	
	public void del(){
		CacheKit.removeAll(CACHENAME);
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			BuyerUser user = BuyerUser.dao.findById(id);
			user.setActive(0);
			user.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	public void undel(){
		CacheKit.removeAll(CACHENAME);
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			BuyerUser user = BuyerUser.dao.findById(id);
			user.setActive(1);
			user.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	public void setReceiver(){
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			BuyerUser user = BuyerUser.dao.findById(id);
			user.setIsReceiver(1);
			user.update();
		}
		rendSuccessJson();
	}
	
	public void setUnReceiver(){
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			BuyerUser user = BuyerUser.dao.findById(id);
			user.setIsReceiver(0);
			user.update();
		}
		rendSuccessJson();
	}
	
}
