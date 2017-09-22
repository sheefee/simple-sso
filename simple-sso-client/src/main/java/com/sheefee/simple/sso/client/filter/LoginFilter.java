package com.sheefee.simple.sso.client.filter;

import java.io.IOException;
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
import com.sheefee.simple.sso.client.storage.SessionStorage;

/**
 * 客户端登录filter
 * 
 * @author sheefee
 * @date 2017年9月11日 下午4:08:25
 *
 */
public class LoginFilter implements Filter {
	private FilterConfig config;

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		
		// 已经登录，放行
		if (session.getAttribute(AuthConst.IS_LOGIN) != null) {
			chain.doFilter(req, res);
			return;
		}
		// 从认证中心回跳的带有token的请求，有效则放行
		String token = request.getParameter(AuthConst.TOKEN);
		if (token != null) {
			session.setAttribute(AuthConst.IS_LOGIN, true);
			session.setAttribute(AuthConst.TOKEN, token);
			// 存储，用于注销
			SessionStorage.INSTANCE.set(token, session);
			chain.doFilter(req, res);
			return;
		}

		// 重定向至登录页面，并附带当前请求地址
		response.sendRedirect(config.getInitParameter(AuthConst.LOGIN_URL) + "?" + AuthConst.CLIENT_URL + "=" + request.getRequestURL());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}