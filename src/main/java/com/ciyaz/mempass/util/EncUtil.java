package com.ciyaz.mempass.util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class EncUtil {

	public static final int saltCount = 100;
	public static final byte[] salt = new byte[] { 2, 16, 123, 5, 87, 40, 31, 9 };
	public static final String algorithm = "PBEWithMD5AndTripleDES";

	/**
	 * 根据密码获取秘钥
	 * 
	 * @param password 密码字符串
	 * @return 秘钥
	 */
	private static Key toKey(String password) {
		SecretKey key = null;
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
		SecretKeyFactory factory;
		try {
			factory = SecretKeyFactory.getInstance(algorithm);
			key = factory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	/**
	 * PBE加密
	 * 
	 * @param data     明文
	 * @param password 密码
	 * @return 密文
	 */
	public static String pbeEncryptStr(String str, String password) {
		byte[] strBytes = str.getBytes();
		byte[] encBytes = pbeEncrypt(strBytes, password);
		return bytesToBase64(encBytes);
	}

	/**
	 * PBE解密
	 * 
	 * @param data     密文
	 * @param password 密码
	 * @return 明文
	 */
	public static String pbeDecryptStr(String str, String password) {
		byte[] encBytes = base64ToBytes(str);
		byte[] bytes = pbeDecrypt(encBytes, password);
		return new String(bytes);
	}

	/**
	 * PBE加密
	 * 
	 * @param data     明文数据
	 * @param password 密码
	 * @return 密文数据
	 */
	public static byte[] pbeEncrypt(byte[] data, String password) {
		byte[] result = null;
		Key key = toKey(password);
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, saltCount);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * PBE解密
	 * 
	 * @param data     密文数据
	 * @param password 密码
	 * @return 明文数据
	 */
	public static byte[] pbeDecrypt(byte[] data, String password) {
		byte[] result = null;
		Key key = toKey(password);
		PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, saltCount);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);
			result = cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 二进制编码为Base64
	 * 
	 * @param data 二进制数据
	 * @return Base64编码
	 */
	public static String bytesToBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	/**
	 * Base64编码解码为二进制数据
	 * 
	 * @param data Base64编码
	 * @return 二进制数据
	 */
	public static byte[] base64ToBytes(String data) {
		return Base64.getDecoder().decode(data);
	}

	/**
	 * SHA256消息摘要
	 * 
	 * @param str 输入信息
	 * @return 散列值
	 */
	public static String sha256Hex(String str) {
		String result = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] resultData = messageDigest.digest(str.getBytes());
			result = encodeHex(resultData);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将二进制数据编码为十六进制数字串
	 * 
	 * @param digest 二进制数据
	 * @return 十六进制数字字符串
	 */
	private static String encodeHex(byte[] digest) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < digest.length; i++) {
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}
