package com.sheefee.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sheefee.simple.sso.client.constant.AuthConst;

@Controller
public class TestController {
	@RequestMapping("/test")
	public String test(HttpServletRequest request, Model model) {
		model.addAttribute(AuthConst.TOKEN, request.getSession().getAttribute(AuthConst.TOKEN));
		return "test";
	}
}