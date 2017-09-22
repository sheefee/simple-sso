package com.sheefee.simple.sso.server.dao;

import org.apache.ibatis.annotations.Param;

import com.sheefee.simple.sso.client.domain.User;

public interface UserDao {
	public User find(@Param("user") User user, @Param("table") String table, @Param("userid") String userid,
			@Param("username") String username, @Param("password") String password);
}