/**
 * 文件名:Prize.java
 * 版本信息:1.0
 * 日期:2015-5-16
 * Copyright 广州点步信息科技
 * 版权所有
 */
package com.dbumama.market.web.core.validator;

import java.util.Enumeration;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * @author: wjun.java@gmail.com
 * @date:2015-5-16
 */
public abstract class BaseJsonRenderValidator extends Validator {
	
	public Logger log = Logger.getLogger(getClass());
	
	/** 检查是否整数 **/
	protected static final String intPatten = "^[1-9]\\d*$";
	
	protected JSONObject errorJson;

	public BaseJsonRenderValidator (){
		errorJson = new JSONObject();
		errorJson.put("Success", false);
		errorJson.put("status", 200);
	}

	@Override
	protected void handleError(Controller c) {
		Enumeration<String> attrNames = c.getAttrNames();
		while(attrNames.hasMoreElements()){
			String attrName = attrNames.nextElement();
			if(attrName.contains("_")){
				String errorKey = attrName.split("_")[0];
				if("error".equals(errorKey)){
					errorJson.put(attrName, c.getAttr(attrName));
				}
			}
		}
		c.renderJson(errorJson);
	}

}
