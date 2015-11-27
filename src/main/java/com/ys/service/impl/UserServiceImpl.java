package com.ys.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.constant.ConstantCode;
import com.ys.dao.UserMapper;
import com.ys.model.User;
import com.ys.service.IUserService;
import com.ys.utils.CommonUtils;

@Service("userService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public Map<String, String> userLogin(String userId, String userPwd) {
		Map<String, String> retMap = new HashMap<>();
		if (userId == null || userId.isEmpty() || userPwd == null || userPwd.isEmpty()) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_EMPTY_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_EMPTY_ERROR_MSG);
			return retMap;
		}
		User user = userMapper.selectByUserId(userId);
		if (user == null) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_LOGIN_ID_NOT_EXISTED_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_LOGIN_ID_NOT_EXISTED_ERROR_MSG);
			return retMap;
		}
		String encryptPwd = CommonUtils.generateMD5(userPwd);
		if (!encryptPwd.equals(user.getUserPwd())) {
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
		Map<String, String> retMap = new HashMap<>();
		if (userId == null || userId.isEmpty() || userPwd == null || userPwd.isEmpty()) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_REGISTER_EMPTY_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_REGISTER_EMPTY_ERROR_MSG);
			return retMap;
		}
		User user = userMapper.selectByUserId(userId);
		if (user != null) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_REGISTER_ID_EXISTED_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_REGISTER_ID_EXISTED_ERROR_MSG);
			return retMap;
		}
		String encryptPwd = CommonUtils.generateMD5(userPwd);
		user = new User();
		user.setUserId(userId);
		user.setUserPwd(encryptPwd);
		if (userMapper.insert(user) != 0) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_REGISTER_SUCCESS_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_REGISTER_SUCCESS_MSG);
		} else {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_REGISTER_DATABASE_OPERATE_ERROR_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_REGISTER_DATABASE_OPERATE_ERROR_ERROR_MSG);
		}
		return retMap;
	}

	@Override
	public Map<String, String> userModifyPwd(String userId, String userOldPwd, String userNewPwd) {
		Map<String, String> retMap = new HashMap<>();
		if (userId == null || userId.isEmpty() || userOldPwd == null || userOldPwd.isEmpty() || userNewPwd == null
				|| userNewPwd.isEmpty()) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_MODIFY_PWD_EMPTY_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_MODIFY_PWD_EMPTY_ERROR_MSG);
			return retMap;
		}
		User user = userMapper.selectByUserId(userId);
		if (user == null) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_MODIFY_PWD_ID_NOT_EXISTED_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_MODIFY_PWD_ID_NOT_EXISTED_ERROR_MSG);
			return retMap;
		}		
		String oldEncryptPwd = CommonUtils.generateMD5(userOldPwd);
		if (!oldEncryptPwd.equals(user.getUserPwd())) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_MODIFY_PWD_OLD_PWD_WRONG_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_MODIFY_PWD_OLD_PWD_WRONG_ERROR_MSG);
			return retMap;
		}
		String newEncryptPwd = CommonUtils.generateMD5(userNewPwd);
		user.setUserPwd(newEncryptPwd);
		if (userMapper.updateUserPwd(user) != 0) {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_MODIFY_PWD_SUCCESS_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_MODIFY_PWD_SUCCESS_MSG);
		} else {
			retMap.put(ConstantCode.ERROR_CODE, ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_CODE);
			retMap.put(ConstantCode.ERROR_MSG, ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_MSG);
		}
		return retMap;
	}
}