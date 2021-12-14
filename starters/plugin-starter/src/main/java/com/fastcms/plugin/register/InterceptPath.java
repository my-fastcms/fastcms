package com.fastcms.plugin.register;

import java.lang.annotation.*;

/**
 * @author： wjun_java@163.com
 * @date： 2021/5/11
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface InterceptPath {

	String[] value();

}
