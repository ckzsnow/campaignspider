package com.ys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ys.constant.ConstantCode;
import com.ys.model.TrainUser;
import com.ys.model.TrainUserModel;
import com.ys.service.ITrainUserService;
import com.ys.utils.TrainUserUtils;
@Controller
public class TrainUserController {

    @Autowired
    private ITrainUserService trainUserService;
    
    /**
     * Util class for insert
     * @param request
     * @return
     * @author Xiaoming
     * @date 2016年1月26日 下午9:33:41
     */
    @RequestMapping("/admin/insertTrainDetails")
    @ResponseBody
    public Map<String, String> insertTrainDetails(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
                List<TrainUser> list = TrainUserUtils.getProperInfo();
                try {
                    for (TrainUser trainUser : list) {
                        map = trainUserService.insertTrainUserDetails(trainUser);
                    }
                    map.put("error_code", "0");
                }catch (Exception e) {
                    map.put("error_code", "1");
                }
              
        return map;
    }
    @RequestMapping("/admin/selectTrainDetails")
    @ResponseBody
    public List<TrainUser> selectTrainDetails(HttpServletRequest request) {
        List<TrainUser>list =  trainUserService.getTrainUserDetails();
        if ((!list.isEmpty()) && list.size() > 0) {
            return list;
        }
        return null;
    }
    @RequestMapping("/admin/updateTrainDetails")
    @ResponseBody
    public Map<String, String> updateTrainDetails(HttpServletRequest request,TrainUserModel trainUserModel) {
        Map<String, String> retMap = new HashMap<>();
        String userId = (String)request.getSession().getAttribute(ConstantCode.USER_SESSION_ID);
        Map<String, String> map = trainUserService.updateTrainUserDetails(userId, trainUserModel);
        if (map.containsKey("error_code_fail")) {
            retMap.put("error_code", "1");
        } else {
            retMap.put("error_code", "0");
        }
        return retMap;
    }
}
