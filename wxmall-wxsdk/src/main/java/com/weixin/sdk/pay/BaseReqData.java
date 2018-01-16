package com.weixin.sdk.pay;

import com.weixin.sdk.kit.WxSdkPropKit;

/**
 * wjun_java@163.com
 * 2015年11月4日
 */
public abstract class BaseReqData extends BaseReqCommData{
	
	private static final long serialVersionUID = 1L;
	protected String appid;
	protected String mch_id;
	protected String notify_url;

	protected BaseReqData (){
		super();
		setAppid(WxSdkPropKit.get("wx_app_id"));
		setMch_id(WxSdkPropKit.get("wx_mch_id"));
		this.setNotify_url(WxSdkPropKit.get("wx_notify_url"));
	}
	
	/**
	 * 支持小程序
	 * @param appId
	 * @param mchId
	 * @param mchSecKey
	 * @param notify_url
	 */
	protected BaseReqData(String appId, String mchId, String mchSecKey,String notify_url){
		super();
		this.appid = appId;
		this.mch_id = mchId;
		this.mch_sec_key = mchSecKey;
		this.notify_url=notify_url;
	}
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

}
