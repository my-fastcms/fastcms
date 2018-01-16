package com.dbumama.market.service.utils;

import java.io.IOException;
import java.util.Locale;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;


public class FreeMarkUtil {
    private static Configuration cfg=null;
	private FreeMarkUtil(){
		
	}
	
	public static Configuration getConfiguration(){
		if(cfg!=null)
			return cfg;
		
		cfg=new Configuration(new Version("2.3.21 "));
		cfg.setClassForTemplateLoading(FreeMarkUtil.class, "/tpl/weipage");
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		
		return cfg;
	}
	
	public static Template getTemplate(String name) throws IOException{
		Configuration myconfig=getConfiguration();
		Template template=myconfig.getTemplate(name);
		return template;
	}
}
