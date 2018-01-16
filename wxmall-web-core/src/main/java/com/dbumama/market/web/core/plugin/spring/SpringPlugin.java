package com.dbumama.market.web.core.plugin.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jfinal.plugin.IPlugin;

/**
 * wjun_java@163.com
 * 2016年6月24日
 */
public class SpringPlugin implements IPlugin {
	private String[] configurations;
	private ApplicationContext ctx;
	
	public String[] getConfigurations() {
		return configurations;
	}
	public void setConfigurations(String[] configurations) {
		this.configurations = configurations;
	}
	public ApplicationContext getCtx() {
		return ctx;
	}
	public void setCtx(ApplicationContext ctx) {
		this.ctx = ctx;
	}

	public SpringPlugin(){
		this(new String []{});
	}
	
	public SpringPlugin(String... configurations) {
		this.configurations = configurations;
	}
	
	public SpringPlugin(ApplicationContext ctx) {
		this.ctx = ctx;
	}
	
	public boolean start() {
		if (ctx != null)
			IocInterceptor.ctx = ctx;
		else if (configurations != null && configurations.length>0)
			IocInterceptor.ctx = new AnnotationConfigApplicationContext(configurations);
		else
			IocInterceptor.ctx = new AnnotationConfigApplicationContext("com.dbumama.market.service");
		return true;
	}
	
	public boolean stop() {
		return true;
	}
}
