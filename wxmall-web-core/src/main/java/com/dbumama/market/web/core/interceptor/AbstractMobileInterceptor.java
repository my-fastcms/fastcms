package com.dbumama.market.web.core.interceptor;

import com.dbumama.market.web.core.config.Wxmall;
import com.dbumama.market.web.core.plugin.spring.IocInterceptor;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 对不需要进行拦截的url进行统一管理
 * wjun_java@163.com
 * 2016年7月9日
 */
public abstract class AbstractMobileInterceptor extends IocInterceptor implements Interceptor{

	final static String exclusions [] = {"/forbid", "/error", "/auth", "/pay/result", "/captcha"};
	
	@Override
	public void intercept(Invocation ai) {
		
		if(!Wxmall.isInstalled()){
			return;
		}
		
		if(!Wxmall.isConfiged()){
			return;
		}
		
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
	
}
