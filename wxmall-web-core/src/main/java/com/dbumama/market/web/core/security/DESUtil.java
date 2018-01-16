package com.dbumama.market.web.core.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.jfinal.kit.StrKit;

public class DESUtil {
	
	private static final byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * 加密
	 * @param encryptString
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {
		if(StrKit.isBlank(encryptString))
			return "";
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
		return new String(Base64.encode(encryptedData));
	}

	/**
	 * 解密
	 * @param decryptString
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		if(StrKit.isBlank(decryptString))
			return "";
		byte[] byteMi = Base64.decode(decryptString);
		IvParameterSpec zeroIv = new IvParameterSpec(iv);
		SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
		byte decryptedData[] = cipher.doFinal(byteMi);
		return new String(decryptedData);
	}
	
	public static void main(String[] args) {
		 try {
			 /*String plaintext = "{\"phone\":\"18621616988\",\"password\":\"000000\"}";
			 String ciphertext = DESUtil.encryptDES(plaintext, Constants.MIMI);
			 System.out.println("明文：" + plaintext);
			 System.out.println("密钥：" + Constants.MIMI);
			 System.out.println("密文：" + ciphertext);
			 System.out.println("解密后：" + DESUtil.decryptDES(ciphertext, Constants.MIMI));*/
			 
			 /*String p = "{\"contentid\":9,\"commenttext\":\"好奇怪好奇怪\"}";
			 System.out.println("加密后：" + DESUtil.encryptDES(p, Constants.MIMI));*/
			 
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
	
}
