package com.dbumama.market.web.core.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

/**
 * wjun.java@gmail.com
 */ 
public class WirelessSecretUtil {

    /**
     * UTF-8字符集 *
     */
    public static final String CHARSET_UTF8 = "UTF-8";

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public final static String getToken(List<Parameter> parameters, String serverSecret) throws IOException {

        if (parameters == null || parameters.size() == 0 || isBlank(serverSecret)) {
            throw new IllegalArgumentException();
        }

        Collections.sort(parameters);

        StringBuffer sb = new StringBuffer("");
        boolean hasAppkey = false;
        for (Parameter parameter : parameters) {
            if (!parameter.getKey().equals("sign")) {
                sb.append(parameter.getKey()).append(parameter.getValue());
            }
            if (parameter.getKey().equals("serverKey")) {
                hasAppkey = true;
            }
        }
        if (!hasAppkey) {
            throw new IllegalArgumentException();
        }
        sb.append("serverSecret" + serverSecret);
        return byte2hex(encryptMD5(sb.toString()));
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
    
    public static String md5(String source) throws IOException{
    	return byte2hex(encryptMD5(source));
    }

}
