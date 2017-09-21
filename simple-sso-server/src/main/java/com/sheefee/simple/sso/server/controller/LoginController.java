package com.sheefee.simple.sso.server.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sheefee.simple.sso.client.constant.AuthConst;

/**
 * 登录和授权控制器
 * 
 * @author sheefee
 * @date 2017年9月12日 下午2:09:47
 *
 */
@Controller
public class LoginController {
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
		// 验证用户
		if (!"sheefee".equals(request.getParameter("username"))) {
			model.addAttribute("error", "user not exist.");
			return "redirect:/";
		}

		// 授权
		String token = UUID.randomUUID().toString();
		request.getSession().setAttribute(AuthConst.IS_LOGIN, true);
		request.getSession().setAttribute(AuthConst.TOKEN, token);

		// 子系统跳转过来的登录请求，授权后，跳转回去
		String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
		if (clientUrl != null && !"".equals(clientUrl)) {
			return "redirect:" + clientUrl + "?" + AuthConst.TOKEN + "=" + token;
		}

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
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}
}