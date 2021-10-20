package com.fastcms.core.jwt;

import java.lang.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/6/7
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiToken {
}
