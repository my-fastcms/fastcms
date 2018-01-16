package com.dbumama.market.web.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 只有的后台正常返回数据的情况下 code值为200 否则为 -200 或者其他
 * @author: wjun.java@gmail.com
 * @date:2015-5-21
 */
public final class ResultUtil {
	
	public final static Map<String, Object> genResult(Object message, int resCode, String resMsg){
		Map<String, Object> map = new HashMap<String, Object>();
	    map.put("message", message);
	    map.put("code", resCode);
	    map.put("msg", resMsg);
	    return map;
	}
	
	public final static Map<String, Object> genSuccessResult() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 200);
        map.put("Success", true);//让浏览器认为是请求成功了
        return map;
    }

    public final static Map<String, Object> genSuccessResult(Object data) {
        Map<String, Object> map = genSuccessResult();
        map.put("data", data);
        return map;
    }

    public final static Map<String, Object> genFailedResult(String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Success", true);
        map.put("code", -200);
        map.put("msg", msg);
        return map;
    }

    public final static Map<String, Object> genFailedResult(int code, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("Success", true);//后台抛出异常也认为是浏览器请求成功了。只是code码不为200，而是 -200
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }
    
    public final static Map<String, Object> genFailedResult(String code, String msg){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("Success", true);
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

    public final static Map<String, Object> genSuccessUrlResult(Object data) {
        Map<String, Object> map = genSuccessResult();
        map.put("url", data);
        return map;
    }
}
