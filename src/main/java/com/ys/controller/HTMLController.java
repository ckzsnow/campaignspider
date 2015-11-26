package com.ys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTMLController {

	@RequestMapping("/")
	public String rootHtml(HttpServletRequest request) {
		return "redirect:/views/login.html";
	}
	
	@RequestMapping("/admin/index")
	public String adminHtml(HttpServletRequest request) {
		return "redirect:/views/index.html";
	}
	
}
