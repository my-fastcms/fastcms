package com.fastcms.core.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 对/fastcms/api/**无需token校验的请求，直接放行jwt校验
 * @author： wjun_java@163.com
 * @date： 2022/3/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PassFastcms {
}
