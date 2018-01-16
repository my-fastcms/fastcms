/**
 * 文件名:SessionInterceptor.java
 * 版本信息:1.0
 * 日期:2015-5-10
 * Copyright 广州盖盖拉信息科技
 * 版权所有
 */
package com.dbumama.market.web.core.interceptor;

import org.apache.log4j.Logger;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

/**
 * 检查登陆session
 * @author: wjun.java@gmail.com
 * @date:2015-5-10
 */
public class SessionInterceptor implements Interceptor {
	
	public Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void intercept(Invocation ai) {
		Controller controller = ai.getController();
		//获取当前请求头的sessionId
		String sessionId = controller.getRequest().getHeader("sessionid");
		if(controller.getSession() != null 
				&& StrKit.notBlank(sessionId) 
				&& sessionId.equals(controller.getSession().getId())){
			ai.invoke();
		} else {
			controller.renderJson("未登陆或已超时");
		}
	}
	
}
