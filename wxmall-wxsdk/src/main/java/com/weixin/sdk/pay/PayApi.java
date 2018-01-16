package com.weixin.sdk.pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.weixin.sdk.kit.HttpKit;

/**
 * 微信支付api基类
 * wjun_java@163.com
 * 2015年11月3日
 */
public abstract class PayApi {

	protected abstract String getApiUrl(); 

	public BaseResData post(BaseReq reqData, byte [] certs) throws Exception{
		String resultXml = HttpKit.post(getApiUrl(), getRequestXml(reqData.toMap()));
    	return getRespone(getResponseRoot(resultXml));
    }
    
	public BaseResData post(BaseReq reqData) throws Exception{
		String resultXml = HttpKit.post(getApiUrl(), getRequestXml(reqData.toMap()));
    	return getRespone(getResponseRoot(resultXml));
    }
	
    /**
	 * 把请求Map参数组成一个xml字符串交给微信接口
	 * @param parameters
	 * @return
	 */
	protected String getRequestXml(Map<String, Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<Entry<String, Object>> es = parameters.entrySet();
		Iterator<Entry<String, Object>> it = es.iterator();
		while(it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
	protected Element getResponseRoot(String responseXml) throws DocumentException{
		Document root = DocumentHelper.parseText(responseXml);
		return root.getRootElement();
	}

	protected abstract BaseResData getRespone(Element root);
	
	protected synchronized String getUUID(){
		return UUID.randomUUID().toString();
	}
	
}
