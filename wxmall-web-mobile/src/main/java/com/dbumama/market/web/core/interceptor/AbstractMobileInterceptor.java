package com.dbumama.market.web.core.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.dbumama.market.model.AuthUser;
import com.dbumama.market.model.CompTicket;
import com.dbumama.market.service.constants.Constants;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

/**
 * 对不需要进行拦截的url进行统一管理
 * wjun_java@163.com
 * 2016年7月9日
 */
public abstract class AbstractMobileInterceptor implements Interceptor{

	final static String exclusions [] = {"/forbid", "/error", "/auth", "/pay/result/", "/pay/result", "/captcha",
			"/weixin/msg", "/weixin/openApi", "/login.jsp", "/followme"};
	
	@Override
	public void intercept(Invocation ai) {
		
		Controller controller = ai.getController();
		String uri = controller.getRequest().getRequestURI();
		for(String exclu : exclusions){
			if(exclu.equals(uri)){
				ai.invoke();
				return;
			}
		}
		
		doIntercept(ai);
		
	}
	
	protected abstract void doIntercept(Invocation inv);

	protected String getAuthAppId(HttpServletRequest request){
		if(PropKit.getBoolean("jfinal.devmode")){
			return PropKit.get("authAppId");
		}
		return getAuthUser(request).getAppId();
	}
	
	protected AuthUser getAuthUser(HttpServletRequest request){
		String serverName = request.getServerName();
		serverName = serverName.substring(0, serverName.indexOf("."));
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(Constants.APPUSER_IN_SESSION);
		if(authUser == null){
			authUser = AuthUser.dao.findFirst("select * from " + AuthUser.table + " where app_id=? and active=1 ", serverName);
			request.getSession().setAttribute(Constants.APPUSER_IN_SESSION, authUser);
		}
		return authUser;
	}
	
	protected String getCompTicket(){
		CompTicket compTicket = CompTicket.dao.findFirst("select * from " + CompTicket.table);
		return compTicket == null ? "" : compTicket.getCompVerifyTicket();
	}
	
}
