/**
 * 文件名:TableBind.java
 * 版本信息:1.0
 * 日期:2015-5-9
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-9
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TableBind {
	
	/** 数据源 **/
	String ds() default "main";
	
	/**表名*/
	String name();
	
	/**主键名*/
	String pk() default "id";
}