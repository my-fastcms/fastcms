package com.dbumama.market.web.core.interceptor;

import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * wjun_java@163.com
 * 2016年7月7日
 */
public class ForbidInterceptor extends AbstractMobileInterceptor {

	/* (non-Javadoc)
	 * @see com.jfinal.aop.Interceptor#intercept(com.jfinal.aop.Invocation)
	 */
	@Override
	public void doIntercept(Invocation inv) {
		Controller controller = inv.getController();
		if(controller instanceof BaseMobileController == false)
			throw new RuntimeException("必须从BaseMobileController继承");
		
		BaseMobileController baseController = (BaseMobileController) controller;
		//check 订购服务是否到期
		SellerUser sellerUser = baseController.getSellerUser();
		if(sellerUser == null)
			throw new RuntimeException("ForbidInterceptor error seller user is null");
		
		if(DateTimeUtil.toDate(DateTimeUtil.getDateTimeString()).after(sellerUser.getEndDate())){
			/*Long buyerCount = Db.queryLong("select count(*) from " + BuyerUser.table + " where active=1 and seller_id=? ", sellerUser.getId());
			inv.getController().setAttr("buyerUsers", buyerCount);*/
			inv.getController().redirect("/forbid");
		}else{
			inv.invoke();
		}
	}
	
}
