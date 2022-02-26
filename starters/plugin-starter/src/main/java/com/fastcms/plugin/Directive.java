package com.fastcms.plugin;

import java.lang.annotation.*;

/**
 * 标识为freemarker标签
 * @author： wjun_java@163.com
 * @date： 2021/5/11
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Directive {

	String[] value();

}
