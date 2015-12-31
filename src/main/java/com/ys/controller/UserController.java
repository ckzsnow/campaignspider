package com.ys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.constant.ConstantCode;
import com.ys.service.IUserService;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    
    @RequestMapping("/userLogin")
    @ResponseBody
    public Map<String, String> userLogin(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        Map<String, String> retMap = userService.userLogin(userId, userPwd);
        if(retMap != null && retMap.containsKey(ConstantCode.ERROR_CODE) && retMap.get(ConstantCode.ERROR_CODE).equals(ConstantCode.USER_LOGIN_SUCCESS_CODE)) {
            request.getSession().setAttribute(ConstantCode.USER_SESSION_ID, userId);
        }
        return retMap;
    }
    
    @RequestMapping("/userRegister")
    @ResponseBody
    public Map<String, String> userRegister(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userPwd = request.getParameter("userPwd");
        Map<String, String> retMap = userService.userRegister(userId, userPwd);
        if(retMap != null && retMap.containsKey(ConstantCode.ERROR_CODE) && retMap.get(ConstantCode.ERROR_CODE).equals(ConstantCode.USER_REGISTER_SUCCESS_CODE)) {
            request.getSession().setAttribute(ConstantCode.USER_SESSION_ID, userId);
        }
        return retMap;
    }
    
    @RequestMapping("/userModifyPwd")
    @ResponseBody
    public Map<String, String> userModifyPwd(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String userOldPwd = request.getParameter("userOldPwd");
        String userNewPwd = request.getParameter("userNewPwd");
        Map<String, String> retMap = userService.userModifyPwd(userId, userOldPwd, userNewPwd);
        if(retMap != null && retMap.containsKey(ConstantCode.ERROR_CODE) && retMap.get(ConstantCode.ERROR_CODE).equals(ConstantCode.USER_MODIFY_PWD_SUCCESS_CODE)) {
            request.getSession().removeAttribute(ConstantCode.USER_SESSION_ID);
        }
        return retMap;
    }
    
    @RequestMapping("/userLogout")
    public String userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(ConstantCode.USER_SESSION_ID);
        return "redirect:/views/login.html";
    }
}
