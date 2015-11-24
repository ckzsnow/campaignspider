package com.ys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.model.User;
import com.ys.service.IUserService;

@Controller
public class UserController {

	@Autowired
	private IUserService userService;
	
	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping("/userLogin")
	@ResponseBody
	public Map<String, String> userLogin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		return retMap;
	}
	
	@RequestMapping("/userRegister")
	@ResponseBody
	public Map<String, String> userRegister(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		Map<String, String> retMap = new HashMap<>();
		if (userId != null && !userId.isEmpty() && userPwd != null
				&& !userPwd.isEmpty()) {
			
			retMap.put("error_code", "0");
		} else {
			retMap.put("error_code", "-1");
		}
		request.getSession().setAttribute("USER_ID", userId);
		return retMap;
	}
	
	@RequestMapping("/userLogout")
	public String userLogout(HttpServletRequest request) {
		request.getSession().removeAttribute("USER_ID");
		return "redirect:/views/.html";
	}
}
