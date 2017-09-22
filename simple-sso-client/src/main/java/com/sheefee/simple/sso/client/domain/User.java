package com.sheefee.simple.sso.client.domain;

/**
 * 用户
 * 
 * @author sheefee
 * @date 2017年9月17日 下午2:47:27
 *
 */
public class User {
	// id
	private String id;
	// 名称
	private String username;
	// 密码
	private String password;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}