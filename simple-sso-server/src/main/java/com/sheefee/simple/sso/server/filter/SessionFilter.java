package com.sheefee.simple.sso.server.filter;

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
import com.sheefee.simple.sso.server.storage.ClientStorage;

/**
 * sso认证中心会话过滤
 * 
 * @author sheefee
 * @since 2017年9月21日 下午9:54:15
 */
public class SessionFilter implements Filter{
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession();
		String uri = request.getRequestURI();
		
		// 注销请求，放行
		if ("/logout".equals(uri)) {
			chain.doFilter(req, res);
			return;
		}
		
		// 已经登录，放行
		if (session.getAttribute(AuthConst.IS_LOGIN) != null) {
			// 如果是客户端发起的登录请求，跳转回客户端，并附带token
			String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
			String token = (String) session.getAttribute(AuthConst.TOKEN);
			if (clientUrl != null && !"".equals(clientUrl)) {
				// 存储，用于注销
				ClientStorage.INSTANCE.set(token, clientUrl);
				response.sendRedirect(clientUrl + "?" + AuthConst.TOKEN + "=" + token);
				return;
			}
			if (!"/success".equals(uri)) {
				response.sendRedirect("/success");
				return;
			}
			chain.doFilter(req, res);
			return;
		}
		// 登录请求，放行
		if ("/".equals(uri) || "/login".equals(uri)) {
			chain.doFilter(req, res);
			return;
		}
		
		// 其他请求，拦截
		response.sendRedirect("/");
	}

	public void init(FilterConfig config) throws ServletException {}
}