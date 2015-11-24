package com.ys.constant;

public interface ErrorCode {

	//user login code
	public static final String USER_LOGIN_EMPTY_ERROR_CODE = "001001";
	public static final String USER_LOGIN_EMPTY_ERROR_MSG = "用户账号或密码为空！";
	public static final String USER_LOGIN_ID_NOT_EXISTED_ERROR_CODE = "001002";
	public static final String USER_LOGIN_ID_NOT_EXISTED_ERROR_MSG = "用户账号不存在！";
	public static final String USER_LOGIN_PWD_WRONG_ERROR_CODE = "001003";
	public static final String USER_LOGIN_PWD_WRONG_ERROR_MSG = "用户密码不正确！";
	public static final String USER_LOGIN_VERIFY_CODE_WRONG_ERROR_CODE = "001004";
	public static final String USER_LOGIN_VERIFY_CODE_WRONG_ERROR_MSG = "验证码不正确！";
	public static final String USER_LOGIN_SUCCESS_CODE = "001000";
	public static final String USER_LOGIN_SUCCESS_MSG = "登陆成功！";
	//user register code
	public static final String USER_REGISTER_EMPTY_ERROR_CODE = "002001";
	public static final String USER_REGISTER_EMPTY_ERROR_MSG = "用户账号或密码为空！";
	public static final String USER_REGISTER_ID_EXISTED_ERROR_CODE = "002002";
	public static final String USER_REGISTER_ID_EXISTED_ERROR_MSG = "用户账号已经存在！";
	public static final String USER_REGISTER_SUCCESS_CODE = "002000";
	public static final String USER_REGISTER_SUCCESS_MSG = "注册成功！";
	//user modify pwd code
	public static final String USER_MODIFY_PWD_EMPTY_ERROR_CODE = "003001";
	public static final String USER_MODIFY_PWD_EMPTY_ERROR_MSG = "用户账号或密码为空！";
	public static final String USER_MODIFY_PWD_ID_NOT_EXISTED_ERROR_CODE = "003002";
	public static final String USER_MODIFY_PWD_ID_NOT_EXISTED_ERROR_MSG = "用户账号不存在！";
	public static final String USER_MODIFY_PWD_PWD_WRONG_ERROR_CODE = "003003";
	public static final String USER_MODIFY_PWD_PWD_WRONG_ERROR_MSG = "用户原始密码不正确！";
	public static final String USER_MODIFY_PWD_SUCCESS_CODE = "003000";
	public static final String USER_MODIFY_PWD_SUCCESS_MSG = "密码修改成功！";
	
}
