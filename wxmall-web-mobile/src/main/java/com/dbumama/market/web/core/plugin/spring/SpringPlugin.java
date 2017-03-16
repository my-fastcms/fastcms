package com.dbumama.market.web.core.plugin.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.IPlugin;

/**
 * wjun_java@163.com
 * 2016年6月24日
 */
public class SpringPlugin implements IPlugin{
	private String[] configurations;
	private ApplicationContext ctx;
	
	/**
	 * Use configuration under the path of WebRoot/WEB-INF.
	 */
	public SpringPlugin() {
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
		else if (configurations != null)
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(configurations);
		else
			IocInterceptor.ctx = new FileSystemXmlApplicationContext(PathKit.getWebRootPath() + "/WEB-INF/applicationContext.xml");
		return true;
	}
	
	public boolean stop() {
		return true;
	}
}
