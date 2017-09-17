package com.sheefee.simple.sso.client.util;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author sheefee
 * @date 2017年9月12日 下午4:54:59
 *
 */
public class RSAUtil {
	private static Key PRIVATE_KEY;
	private static Key PUBLIC_KEY;

	private static final String ALGORITHM = "RSA";
	private static final int KEYSIZE = 1024;

	static {
		genKeyPair();
	}

	/**
	 * 生成RSA密钥对
	 */
	public static void genKeyPair() {
		KeyPairGenerator keyPairGenerator = null;
		try {
			keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGenerator.initialize(KEYSIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PRIVATE_KEY = keyPair.getPrivate();
		PUBLIC_KEY = keyPair.getPublic();
	}

	/**
	 * 使用RSA加密字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String encode(String src) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, PUBLIC_KEY);
			return Base64.encodeBase64String(cipher.doFinal(src.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 使用RSA解密字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String decode(String src) {
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, PRIVATE_KEY);
			return new String(cipher.doFinal(Base64.decodeBase64(src)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}