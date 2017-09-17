package com.sheefee.simple.sso.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	// 存储令牌及其关联的客户端系统信息，此种方式用于开发测试，生产环境最好用单独的服务器存储，如redis，甚至MySQL也可以
	private ConcurrentHashMap<String, List<String>> map = new ConcurrentHashMap<String, List<String>>();

	public void register(String token, String url) {
		if (map.get(token) == null) {
			ArrayList<String> list = new ArrayList<String>();
			list.add(url);
			map.put(token, list);
			return;
		}
		map.get(token).add(url);
	}

	public List<String> remove(String token) {
		return map.remove(token);
	}
}