package com.sheefee.simple.sso.server.service;

import java.util.List;

/**
 * 授权服务类
 * 
 * @author sheefee
 * @date 2017年9月12日 下午2:50:00
 *
 */
public interface AuthService {
	/**
	 * 创建的token用于哪些客户端，这里统一登记
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午4:21:08
	 * @param token
	 * @param url
	 */
	public void register(String token, String url);

	/**
	 * 获取并移除token关联的客户端URL列表
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午4:33:43
	 * @param token
	 * @return
	 */
	public List<String> remove(String token);
}