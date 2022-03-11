package com.fastcms.core.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解标记的Controller method直接跳过spring security
 * 用该注解标记之后，不可使用 {@link com.fastcms.core.auth.AuthUtils} 获取用户信息
 * @author： wjun_java@163.com
 * @date： 2022/3/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PassFastcms {
}
