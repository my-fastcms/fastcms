package com.fastcms.core.mybatis;

import java.lang.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/31
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface DataPermission {

	String value() default "";

}
