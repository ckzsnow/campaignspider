package com.ys.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping("/userLogin")
	@ResponseBody
	public Map<String, String> userLogin(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		Map<String, String> retMap = new HashMap<>();
		if (userId != null && !userId.isEmpty() && userPwd != null
				&& !userPwd.isEmpty()) {
			logger.debug("user_id = {}, user_pwd = {}.");
			retMap.put("error_code", "0");
		} else {
			retMap.put("error_code", "-1");
		}
		request.getSession().setAttribute("USER_ID", userId);
		return retMap;
	}
}
