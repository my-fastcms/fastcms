package com.weixin.sdk.pay;

import java.lang.reflect.Field;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;

import com.weixin.sdk.utils.SignKit;

/**
 * wjun_java@163.com
 * 2015年12月11日
 */
public abstract class BaseReqCommData implements BaseReq{

	private static final long serialVersionUID = 1L;
	protected String sign;
	protected String nonce_str;
	protected String mch_sec_key; //商户支付密钥
	
	public BaseReqCommData(){
		setNonce_str(SignKit.genRandomString32());
	}
	
	public TreeMap<String,Object> toMap(){
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        Field [] superFields = this.getClass().getSuperclass().getDeclaredFields();
        Field [] fields = this.getClass().getDeclaredFields();
        Field [] allFields = ArrayUtils.addAll(superFields, fields);
        //获取父类的父类属性
        Field [] superFieldss = this.getClass().getSuperclass().getSuperclass().getDeclaredFields();
        if(superFieldss !=null){
        	allFields = ArrayUtils.addAll(superFieldss, allFields);
        }
        for (Field field : allFields) {
        	field.setAccessible(true);
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                	if(!"serialVersionUID".equals(field.getName()) && !"mch_sec_key".equals(field.getName()))
                		map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	
	public String getMch_sec_key() {
		return mch_sec_key;
	}
	public void setMch_sec_key(String mch_sec_key) {
		this.mch_sec_key = mch_sec_key;
	}
	
}
