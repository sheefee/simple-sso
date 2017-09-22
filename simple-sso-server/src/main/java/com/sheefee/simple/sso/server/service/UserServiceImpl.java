package com.sheefee.simple.sso.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheefee.simple.sso.client.domain.User;
import com.sheefee.simple.sso.server.dao.UserDao;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	
	public User find(User user) {
		return userDao.find(user);
	}
}