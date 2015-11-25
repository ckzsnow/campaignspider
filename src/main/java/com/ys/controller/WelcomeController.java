package com.ys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	@RequestMapping("/")
	public String userLogout(HttpServletRequest request) {
		return "redirect:/views/login.html";
	}
	
}
