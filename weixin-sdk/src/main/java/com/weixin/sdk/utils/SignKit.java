package com.weixin.sdk.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Random;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.weixin.sdk.encrypt.SHA1;
import com.weixin.sdk.kit.WxSdkPropKit;

public class SignKit {
	
	/**
     * UTF-8字符集 *
     */
    public static final String CHARSET_UTF8 = "UTF-8";
    
    /**
     *  treeMap 保证按字母排序 
     * @param para
     * @return
     */
    public static String sign(TreeMap<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		for(String key : params.keySet()){
			String value = (String) params.get(key);
			if(!"sign".equals(key) && StringUtils.isNotBlank(value)) {
				sb.append(key + "=" + value + "&");
			}
		}
		try {
			return md5(sb.append("key=").append(WxSdkPropKit.get("wx_secret_key")).toString()).toUpperCase();
		} catch (IOException e) {
			return null;
		}
	}
    
    public static String signSHA1(TreeMap<String, Object> params){
    	StringBuffer sbkey = new StringBuffer();
		for (String key : params.keySet()) {
			String value = (String) params.get(key);
			if (!"sign".equals(key) && StringUtils.isNotBlank(value)) {
				sbkey.append(key + "=" + value + "&");
			}
		}
		sbkey = sbkey.deleteCharAt(sbkey.length() - 1);
		return SHA1.getSha1(sbkey.toString());
    }
    
    /**
     *  treeMap 保证按字母排序 
     * @param para
     * @return
     */
    public static String signForShared(TreeMap<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		for(String key : params.keySet()){
			String value = (String) params.get(key);
			if(!"sign".equals(key) && StringUtils.isNotBlank(value)) {
				sb.append(key + "=" + value + "&");
			}
		}
		try {
			sb=sb.deleteCharAt(sb.length()-1);
			return md5(sb.toString()).toUpperCase();
		} catch (IOException e) {
			return null;
		}
	}

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    
    public static String genRandomString32(){
    	return getRandomStringByLength(32);
    }
    
    public static String md5(String source) throws IOException{
    	return byte2hex(encryptMD5(source));
    }
	
	private static String getStringFromException(Throwable e) {
        String result = "";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(bos);
        e.printStackTrace(ps);
        try {
            result = bos.toString(CHARSET_UTF8);
        } catch (IOException ioe) {
        }
        return result;
    }
	
	private static byte[] encryptMD5(String data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes(CHARSET_UTF8));
        } catch (GeneralSecurityException gse) {
            String msg = getStringFromException(gse);
            throw new IOException(msg);
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
    
}
