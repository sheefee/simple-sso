package com.sheefee.simple.sso.server.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sheefee.simple.sso.client.constant.AuthConst;

/**
 * 认证中心页面显示控制器
 * 
 * @author sheefee
 * @date 2017年9月12日 下午2:17:19
 *
 */
@Controller
public class IndexController {
	/**
	 * 登录页面
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午2:17:51
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model) {
		model.addAttribute(AuthConst.CLIENT_URL, request.getParameter(AuthConst.CLIENT_URL));
		return "index";
	}

	/**
	 * 登录成功页面
	 * 
	 * @author sheefee
	 * @date 2017年9月12日 下午2:18:02
	 * @return
	 */
	@RequestMapping("/success")
	public String success() {
		return "success";
	}
}