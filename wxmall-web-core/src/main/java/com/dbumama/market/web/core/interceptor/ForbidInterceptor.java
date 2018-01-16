package com.dbumama.market.web.core.interceptor;

import com.dbumama.market.model.SellerUser;
import com.dbumama.market.service.api.user.SellerUserService;
import com.dbumama.market.service.utils.DateTimeUtil;
import com.dbumama.market.web.core.controller.BaseMobileController;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * wjun_java@163.com
 * 2016年7月7日
 */
public class ForbidInterceptor extends AbstractMobileInterceptor {

	private SellerUserService sellerUserService = (SellerUserService) ctx.getBean("sellerUserService");
	
	/* (non-Javadoc)
	 * @see com.jfinal.aop.Interceptor#intercept(com.jfinal.aop.Invocation)
	 */
	@Override
	public void doIntercept(Invocation inv) {
		Controller controller = inv.getController();
		if(controller instanceof BaseMobileController){
			BaseMobileController baseController = (BaseMobileController) controller;
			SellerUser sellerUser = sellerUserService.findById(baseController.getSellerId());
			if(sellerUser != null){
				if(DateTimeUtil.toDate(DateTimeUtil.getDateTimeString()).after(sellerUser.getEndDate())){
					inv.getController().redirect("/forbid");
					return;
				}	
			}
		}
		inv.invoke();
	}
	
}
