package com.weixin.sdk.utils;

import com.weixin.sdk.json.FastJson;
import com.weixin.sdk.json.Json;

/**
 * Json转换
 * 默认使用jackson
 * 再次fastJson
 * 最后使用jsonKit
 *
 * @author L.cm
 * email: 596392912@qq.com
 * site:http://www.dreamlu.net
 * date 2015年5月13日下午4:58:33
 */
public final class JsonUtils {
	
	private JsonUtils() {}
	
	// Json
	private static final Json json;
	
	static {
		Json jsonToUse = null;
		// com.fasterxml.jackson.databind.ObjectMapper?
		if (ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", JsonUtils.class.getClassLoader())) {
			jsonToUse = new JsonUtils.Jackson();
		}
		// com.alibaba.fastjson.JSONObject?
		else if (ClassUtils.isPresent("com.alibaba.fastjson.JSONObject", JsonUtils.class.getClassLoader())) {
			jsonToUse = new FastJson();
		}
		// Jackson
		else {
			jsonToUse = new Jackson();
		}
		json = jsonToUse;
	}
	
	/**
	 * 解决微信特殊字符的乱码
	 */
	private static class Jackson extends Json {
		private final com.fasterxml.jackson.databind.ObjectMapper objectMapper;
		
		public Jackson() {
			this.objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
			this.objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
			this.objectMapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
		}
		
		@Override
		public String toJson(Object object) {
			try {
				return objectMapper.writeValueAsString(object);
			} catch (Exception e) {
				throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
			}
		}
		
		@Override
		public <T> T parse(String jsonString, Class<T> type) {
			try {
				return objectMapper.readValue(jsonString, type);
			} catch (Exception e) {
				throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
			}
		}
		
	} 
	
	/**
	 * 将 Object 转为json字符串
	 * @param record
	 * @return JsonString
	 */
	public static String toJson(Object object) {
		if (json == null) {
			throw new RuntimeException("Jackson, Fastjson or JFinalJson not supported");
		}
		return json.toJson(object);
	}
	
	/**
	 * 将 json字符串 转为Object
	 * @param jsonString
	 * @param valueType
	 * @return T
	 */
	public static <T> T parse(String jsonString, Class<T> valueType) {
		if (json == null) {
			throw new RuntimeException("Jackson, Fastjson not supported");
		}
		return json.parse(jsonString, valueType);
	}
	
}
