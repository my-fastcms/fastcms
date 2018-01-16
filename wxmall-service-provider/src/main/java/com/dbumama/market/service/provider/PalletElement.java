package com.dbumama.market.service.provider;

import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

@SuppressWarnings("serial")
public class PalletElement implements Serializable{
	String label;	//显示文本
	String key;		//JSON数据 key值
	String type;	//类型 分table span div img等
	String text;	//显示文本
	String imgSrc;	//当type为img的时候有值
	String url;		//加载数据的url
	
	public PalletElement(String label, String key) {
		this(label, key, "");
	}
	
	public PalletElement(String label, String key, String text) {
		this(label, key, "", text, "");
	}
	
	public PalletElement(String label, String key, String type, String url) {
		this(label, key, type, "", url);
	}
	
	public PalletElement(String label, String key, String type, String text, String url) {
		super();
		this.label = label;
		this.key = key;
		this.type = type;
		this.text = text;
		this.url = url;
		if("img".equals(this.type)){
			OkHttpClient okHttpClient = new OkHttpClient();
			Request request = new Request.Builder().
	                url(getUrl())
	                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116Safari/537.36")
	                .build();
			try {
				Response okResponse = okHttpClient.newCall(request).execute();
				Base64 base64 = new Base64();
	            final String result = "data:image/jpg;base64," + base64.encodeAsString(okResponse.body().bytes());
	            setImgSrc(result);
			} catch (IOException e) {
				e.printStackTrace();
				setImgSrc("");
			}
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
