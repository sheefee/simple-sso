package com.sheefee.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@RequestMapping("/test")
	public String test(HttpServletRequest request, Model model) {
		return "test";
	}
	@RequestMapping("/success")
	public String success(HttpServletRequest request, Model model) {
		return "success";
	}
}