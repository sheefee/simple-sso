package com.sheefee.simple.sso.server.dao;

import com.sheefee.simple.sso.client.domain.User;

public interface UserDao {
	public User find(User user);
}