package com.sheefee.simple.sso.client.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sheefee.simple.sso.client.constant.AuthConst;
import com.sheefee.simple.sso.client.util.AuthUtil;

/**
 * 
 * @author sheefee
 * @date 2017年9月11日 下午4:08:25
 *
 */
public class AuthFilter implements Filter {
	private FilterConfig config;
	// 存储token及其创建的session
	private ConcurrentHashMap<String, HttpSession> map;
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		// 注销请求
		if (request.getParameter(AuthConst.LOGOUT_REQUEST) != null) {
			HttpSession activeSession = map.get(request.getParameter(AuthConst.LOGOUT_REQUEST));
			String token = (String) activeSession.getAttribute(AuthConst.TOKEN);
			// 请求认证中心注销
			Map<String, String> params = new HashMap<String, String>();
			params.put(AuthConst.TOKEN, token);
			params.put(AuthConst.LOGOUT_REQUEST, AuthConst.LOGOUT_REQUEST);
			AuthUtil.post(config.getInitParameter(AuthConst.LOGIN_URL), params);
			// 注销本地会话
			if (activeSession != null) {
				activeSession.invalidate();
			}
		}
		
		// 已登录，放行
		if (session.getAttribute(AuthConst.IS_LOGIN) != null) {
			chain.doFilter(req, res);
			return;
		}
		// 从认证中心回跳的带有token的请求，有效则放行
		String token = request.getParameter(AuthConst.TOKEN);
		if (token != null) {
			// 将局部会话标记为“已登录”
			session.setAttribute(AuthConst.IS_LOGIN, true);
			// 保存局部会话与token的对应关系
			map.put(token, session);
			
			chain.doFilter(req, res);
			return;
		}
		
		// 重定向至登录页面，并附带当前请求地址
		response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "=" + request.getRequestURL());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		map = new ConcurrentHashMap<String, HttpSession>();
	}
}