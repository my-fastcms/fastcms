package com.fastcms.core.permission;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/4/4
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface AdminMenu {

	@AliasFor("name")
	String value() default "";

	String name() default "";

	String icon() default "<i class=\"nav-icon fas fa-th\"></i>";

	int sort() default 0;

	String type() default "menu";

	String module() default "";

}
