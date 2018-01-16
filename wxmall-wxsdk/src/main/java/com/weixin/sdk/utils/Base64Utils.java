package com.weixin.sdk.utils;

import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

/**
 * JDK6之后 Base64工具
 * 
 * 参考：http://www.importnew.com/14961.html
 */
public class Base64Utils {
	
	private Base64Utils() {}
	
	private static Charset CHARSET = Charset.forName("utf-8");
	
	/**
	 * 编码
	 */
	public static String encode(byte[] val) {
		return DatatypeConverter.printBase64Binary(val);
	}
	
	/**
	 * 编码
	 */
	public static String encode(String value) {
		byte[] val = value.getBytes(CHARSET);
		return DatatypeConverter.printBase64Binary(val);
	}
	
	/**
	 * 编码
	 */
	public static String encode(String value, String charsetName) {
		byte[] val = value.getBytes(Charset.forName(charsetName));
		return DatatypeConverter.printBase64Binary(val);
	}
	
	/**
	 * 解码
	 */
	public static byte[] decodeBase64(String value) {
		return DatatypeConverter.parseBase64Binary(value);
	}
	
	/**
	 * 解码
	 */
	public static String decode(String value) {
		byte[] decodedValue = Base64Utils.decodeBase64(value);
		return new String(decodedValue, CHARSET);
	}
	
	/**
	 * 解码
	 */
	public static String decode(String value, String charsetName) {
		byte[] decodedValue = Base64Utils.decodeBase64(value);
		return new String(decodedValue, Charset.forName(charsetName));
	}
	
}