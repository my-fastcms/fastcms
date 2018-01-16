/**
 * 文件名:FansController.java
 * 版本信息:1.0
 * 日期:2015-6-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.admin.customer.controller;

import com.dbumama.market.model.BuyerUser;
import com.dbumama.market.service.api.customer.CustomerException;
import com.dbumama.market.service.api.customer.CustomerParamDto;
import com.dbumama.market.service.api.customer.CustomerService;
import com.dbumama.market.service.api.customer.MemberResultDto;
import com.dbumama.market.service.api.user.UserService;
import com.dbumama.market.web.admin.customer.validator.BuyerUserValidator;
import com.dbumama.market.web.core.annotation.RouteBind;
import com.dbumama.market.web.core.controller.AdminBaseController;
import com.dbumama.market.web.core.plugin.spring.Inject.BY_NAME;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.ehcache.CacheKit;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-6-9
 */
@RouteBind(path="customer", viewPath="customer")
public class CustomerController extends AdminBaseController<BuyerUser>{

	static final String CACHENAME = "/customer/list";
	static final String CACHENAME_KEY = "customer_key";
	
	@BY_NAME
	private CustomerService customerService;
	@BY_NAME
	private UserService userService;
	
	/* (non-Javadoc)
	 * @see com.dbumama.market.web.common.controller.AdminBaseController#getModelClass()
	 */
	@Override
	protected Class<BuyerUser> getModelClass() {
		return BuyerUser.class;
	}
	
	public void index(){
		render("customer_index.html");
	}
	
	public void members(){
		render("customer_member.html");
	}
	
	public void listmember(){
		CustomerParamDto params = new CustomerParamDto(getSellerId(), getPageNo());
		params.setNickName(getPara("qname"));
		params.setActive(getParaToInt("active"));
		try {
			Page<MemberResultDto> members = customerService.listMembers(params);
			rendSuccessJson(members);	
		} catch (CustomerException e) {
			rendFailedJson(e.getMessage());
		}		
	}
	
	public void list(){
		CustomerParamDto params = new CustomerParamDto(getSellerId(), getPageNo());
		params.setNickName(getPara("qname"));
		params.setActive(getParaToInt("active"));
		params.setSceneId(getParaToInt("sceneId"));
		try {
			Page<BuyerUser> buyerUser = customerService.list(params);
			rendSuccessJson(buyerUser);	
		} catch (CustomerException e) {
			rendFailedJson(e.getMessage());
		}		
	}
	
	public void detail(){
		BuyerUser buser = userService.findById(getParaToLong("id"));
		setAttr("customer", buser);
		render("customer_detail.html");
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
			BuyerUser user = userService.findById(Long.valueOf(id));
			user.setActive(0);
			user.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	public void undel(){
		CacheKit.removeAll(CACHENAME);
		String ids = getPara("ids");
		for(String id : ids.split("-")){
			BuyerUser user = userService.findById(Long.valueOf(id));
			user.setActive(1);
			user.update();
		}
		rendSuccessJson("操作成功！");
	}
	
	public void selectCustomer(){
		render("customer_select.html");
	}
	
}
