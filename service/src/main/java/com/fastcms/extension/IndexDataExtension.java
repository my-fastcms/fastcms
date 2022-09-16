package com.fastcms.extension;

import org.pf4j.ExtensionPoint;

import java.util.Map;

/**
 * 管理后台首页数据扩展
 * @author： wjun_java@163.com
 * @date： 2022/1/8
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface IndexDataExtension extends ExtensionPoint {

	/**
	 * 获取需要展示到首页的数据
	 * @return
	 */
	Map<String, Object> getData();

}
