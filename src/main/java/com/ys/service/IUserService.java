package com.ys.service;

import java.util.Map;

import com.ys.model.Campaign;

public interface IUserService {

    public Map<String, String> userLogin(String userId, String userPwd);
    
    public Map<String, String> userRegister(String userId, String userPwd);
    
    public Map<String, String> userModifyPwd(String userId, String userOldPwd, String userNewPwd);
    
    
}
