/**
 * 文件名:RouteBind.java
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
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface RouteBind {
	/**对应的路径名 已/开头*/
	String path() default"/";
	/**视图所在目录*/
	String viewPath() default "";
	/**名称*/
	String name()default "";
	/**系统名称*/
	String sys() default "";
	/**模块*/
	String model()default "";
	/**编码 5位编码 可用于绑定权限*/
	String code()default "";
}
