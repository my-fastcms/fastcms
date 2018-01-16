package com.dbumama.market.web.core.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

/**
 * 对某些数据接口进行防止重复请求
 * @author wangjun
 *
 */
public class RepeatRequestInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		inv.invoke();
	}

}
