package com.sheefee.simple.sso.client.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import com.sheefee.simple.sso.client.util.HTTPUtil;

/**
 * 客户端注销filter
 * 
 * @author sheefee
 * @date 2017年9月11日 下午4:08:25
 *
 */
public class LogoutFilter implements Filter {
	private FilterConfig config;

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		
		String logoutUrl = config.getInitParameter(AuthConst.LOGOUT_URL);
		String token = (String) session.getAttribute(AuthConst.TOKEN);
		
		// 主动注销，即子系统提供的注销请求
		if ("/logout".equals(request.getRequestURI())) {
			// 向认证中心发送注销请求
			Map<String, String> params = new HashMap<String, String>();
			params.put(AuthConst.LOGOUT_REQUEST, token);
			HTTPUtil.post(logoutUrl, params);
			// 注销后重定向
			response.sendRedirect("/test");
			// 注销本地会话
			session = SessionStorage.INSTANCE.get(token);
			if (session != null) {
				session.invalidate();
			}
			return;
		}
		
		// 被动注销，即从认证中心发送的注销请求
		token = request.getParameter(AuthConst.LOGOUT_REQUEST);
		if (token != null && !"".equals(token)) {
			session = SessionStorage.INSTANCE.get(token);
			if (session != null) {
				session.invalidate();
			}
		}
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}
}