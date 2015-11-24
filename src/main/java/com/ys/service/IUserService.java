package com.ys.service;

import java.util.Map;

public interface IUserService {

	public Map<String, String> userLogin(String userId, String userPwd);
	
	public Map<String, String> userRegister(String userId, String userPwd);
	
}
