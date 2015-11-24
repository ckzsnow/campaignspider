package com.ys.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.constant.ConstantCode;
import com.ys.dao.UserMapper;
import com.ys.model.User;
import com.ys.service.IUserService;
import com.ys.utils.GenerateMD5;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Map<String, String> userLogin(String userId, String userPwd) {
		Map<String, String> retMap = new HashMap<>();
		if(userId == null || userId.isEmpty() || userPwd == null || userPwd.isEmpty()) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_EMPTY_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_EMPTY_ERROR_CODE);
			return retMap;
		}
		User user = userMapper.selectByUserId(userId);
		if(user == null) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_ID_NOT_EXISTED_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_ID_NOT_EXISTED_ERROR_MSG);
			return retMap;
		}
		String encryptPwd;
		try {
			encryptPwd = GenerateMD5.generateMD5(userPwd);
		} catch (NoSuchAlgorithmException e) {
			encryptPwd = userPwd;
		}
		if(encryptPwd != user.getUserPwd()) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_PWD_WRONG_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_PWD_WRONG_ERROR_MSG);
		} else {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_SUCCESS_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_SUCCESS_MSG);
		}		
		return retMap;
	}

	@Override
	public Map<String, String> userRegister(String userId, String userPwd) {
		return null;
	}

	@Override
	public Map<String, String> userModifyPwd(String userId, String userOldPwd, String userNewPwd) {
		return null;
	}

}
