package com.dbumama.market.web.core.plugin.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * wjun_java@163.com
 * 2016年6月24日
 */
public class Inject {
	private Inject() {}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public static @interface BY_TYPE {}
	
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public static @interface BY_NAME {}
	
	/*
	@Inherited
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.FIELD})
	public static @interface IGNORE {}
	*/
}
