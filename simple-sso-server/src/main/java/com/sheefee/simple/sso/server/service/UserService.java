package com.sheefee.simple.sso.server.service;

import com.sheefee.simple.sso.client.domain.User;

public interface UserService {
	/**
	 * 根据username和password查找数据库中的用户并返回
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午2:24:42
	 * @param user
	 * @return User
	 */
	public User find(User user);
}