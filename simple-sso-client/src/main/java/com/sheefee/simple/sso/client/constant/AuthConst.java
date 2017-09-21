package com.sheefee.simple.sso.client.constant;

/**
 * 授权过程中涉及到的常量字符串
 * 
 * @author sheefee
 * @date 2017年9月17日 下午2:46:07
 *
 */
public interface AuthConst {
	// 会话是否授权标志
	public static String IS_LOGIN = "isLogin";
	// 登录中心url
	public static String LOGIN_URL = "loginUrl";
	// 注销url
	public static String LOGOUT_URL = "logoutUrl";
	// 客户端url
	public static String CLIENT_URL = "clientUrl";
	// 授权令牌
	public static String TOKEN = "token";
	// 注销请求
	public static String LOGOUT_REQUEST = "logoutRequest";
}