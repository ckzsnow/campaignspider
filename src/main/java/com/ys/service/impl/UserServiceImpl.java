package com.ys.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.dao.UserMapper;
import com.ys.model.User;
import com.ys.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Map<String, String> userLogin(String userId, String userPwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> userRegister(String userId, String userPwd) {
		// TODO Auto-generated method stub
		return null;
	}

}
