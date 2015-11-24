package com.ys.dao;

import com.ys.model.User;

public interface UserMapper {
	
    int deleteByUserId(String userId);

    int insert(User record);

    User selectByUserId(String userId);

    int updateUserPwd(User record);
    
}