package com.dbumama.market.web.core.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dbumama.market.model._MappingKit;
import com.dbumama.market.web.core.plugin.shiro.ShiroInterceptor;
import com.dbumama.market.web.core.plugin.shiro.ShiroPlugin;
import com.dbumama.market.web.core.plugin.shiro.freemarker.ShiroTags;
import com.dbumama.market.web.core.plugin.spring.SpringDataSourcePlugin;
import com.dbumama.market.web.core.plugin.spring.SpringPlugin;
import com.dbumama.market.web.core.route.AutoBindRoutes;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.FreeMarkerRender;
import com.weixin.sdk.kit.WxSdkPropKit;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.StandardCompress;

public final class JFWebConfig extends JFinalConfig{

	private Logger logger = Logger.getLogger(JFWebConfig.class);
	private Routes routes;
	@Override
	public void configConstant(Constants me) {
		loadPropertyFile("system.properties");
		PropKit.use("system.properties");
		
//		me.setBaseUploadPath("/");
		me.setEncoding("UTF-8");
		me.setI18nDefaultBaseName("i18n");
		me.setMaxPostSize(1024 * 1024 * 5);
		me.setI18nDefaultLocale(getProperty("locale"));
		me.setError401View("/404.html");
		me.setError403View("/404.html");
		me.setError404View("/404.html");
		me.setError500View("/500.html");
	}

	@Override
	public void configRoute(Routes me) {
		this.routes = me;
		routes.add(new AutoBindRoutes());	
	}

	@Override
	public void configPlugin(Plugins me) {
		//spring plugin
		String [] configurations = new String [] {"classpath:applicationContext.xml"};
		SpringPlugin springPlugin = new SpringPlugin(configurations);
		me.add(springPlugin);
		
		/*DruidPlugin dataSourcePlugin = new DruidPlugin(getProperty("jdbc.url"), getProperty("jdbc.username"), getProperty("jdbc.password").trim());
		me.add(dataSourcePlugin);*/
		//添加自动绑定model与表插件
		ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(new SpringDataSourcePlugin());
		activeRecordPlugin.setShowSql(true);
		activeRecordPlugin.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		_MappingKit.mapping(activeRecordPlugin);
		me.add(activeRecordPlugin);
		
		//缓存插件
		me.add(new EhCachePlugin());
		
		me.add(new ShiroPlugin(routes));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		me.add(new ShiroInterceptor());
		//me.add(new VersionInterceptor());
	}

	@Override
	public void configHandler(Handlers me) {}

	@Override
	public void afterJFinalStart() {
		
		Configuration cf = FreeMarkerRender.getConfiguration();
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("imgDomain", PropKit.get("img_domain"));
    	map.put("webctx", JFinal.me().getContextPath());
		map.put("locale", getProperty("locale"));
    	map.put("compress", StandardCompress.INSTANCE);
    	try {
			cf.setSharedVaribles(map);
		} catch (TemplateModelException e) {
			e.printStackTrace();
		}
    	if(getPropertyToBoolean("jfinal.devmode")){
    		cf.setTemplateUpdateDelay(getPropertyToInt("template.update_delay", 0));	
    	}else{
    		cf.setTemplateUpdateDelay(getPropertyToInt("template.update_delay", 300));
    	}
    	cf.setDefaultEncoding(getProperty("template.encoding"));
    	cf.setURLEscapingCharset(getProperty("url_escaping_charset"));
    	cf.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    	cf.setWhitespaceStripping(true);
    	cf.setClassicCompatible(true);
    	cf.setNumberFormat(getProperty("template.number_format"));
    	cf.setBooleanFormat(getProperty("template.boolean_format"));
    	cf.setDateTimeFormat(getProperty("template.datetime_format"));
    	cf.setDateFormat(getProperty("template.date_format"));
    	cf.setTimeFormat(getProperty("template.time_format"));
    	
    	cf.setSharedVariable("shiro", new ShiroTags());
    	cf.setServletContextForTemplateLoading(JFinal.me().getServletContext(), getProperty("template.loader_path"));
    	
		
		loadPropertyFile("weixin.sdk.properties");
		WxSdkPropKit.use("weixin.sdk.properties");
		
		logger.info(" dbu admin server is ready!!");
		super.afterJFinalStart();
	}
	
	@Override
	public void beforeJFinalStop() {
		super.beforeJFinalStop();
	}

}
