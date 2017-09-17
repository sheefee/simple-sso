package com.sheefee.simple.sso.client.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author sheefee
 * @date 2017年9月12日 下午4:55:46
 *
 */
public class MD5Util {
	/**
	 * MD5加密字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String encode(String src) {
		return DigestUtils.md5Hex(src);
	}
}