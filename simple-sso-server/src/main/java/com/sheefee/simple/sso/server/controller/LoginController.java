package com.sheefee.simple.sso.server.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sheefee.simple.sso.client.constant.AuthConst;
import com.sheefee.simple.sso.client.util.AuthUtil;
import com.sheefee.simple.sso.server.service.AuthService;

/**
 * 登录和授权控制器
 * 
 * @author sheefee
 * @date 2017年9月12日 下午2:09:47
 *
 */
@Controller
public class LoginController {
	@Autowired
	private AuthService authService;

	/**
	 * 登录
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午2:09:24
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		// 验证用户信息
		if (!"sheefee".equals(request.getParameter("username"))) {
			model.addAttribute("error", "user not exist.");
			return "redirect:/";
		}

		// 创建授权令牌，将会话标记为已登录，并将token放于会话中
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute(AuthConst.IS_LOGIN, true);
		request.getSession().setAttribute(AuthConst.TOKEN, token);

		// 如果是客户端发起的登录请求，跳转回客户端，并附带token
		String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
		if (clientUrl != null && !"".equals(clientUrl)) {
			// 存储token与客户端url的关联关系
			authService.register(token, clientUrl);
			return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
		}

		// 认证中心发起的登录请求，跳转至登录成功页面
		return "redirect:/";
	}

	/**
	 * 注销
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午2:08:51
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		// 注销本地会话
		if (session != null) {
			session.invalidate();
		}
		
		// 如果是客户端发起的注销请求
		String token = (String) session.getAttribute(AuthConst.TOKEN);
		List<String> clientUrls = authService.remove(token);
		if (clientUrls != null && clientUrls.size() > 0) {
			Map<String, String> params = new HashMap<String, String>();
			params.put(AuthConst.LOGOUT_REQUEST, AuthConst.LOGOUT_REQUEST);
			params.put(AuthConst.TOKEN, token);
			for (String url : clientUrls) {
				AuthUtil.post(url, params);
			}
		}
		// 重定向到登录页面
		return "redirect:/";
	}
}