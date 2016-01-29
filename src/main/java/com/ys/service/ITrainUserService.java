package com.ys.service;

import java.util.List;
import java.util.Map;

import com.ys.model.TrainUser;
import com.ys.model.TrainUserModel;

public interface ITrainUserService {

    public Map<String,String> insertTrainUserDetails(TrainUser trainUser);
    
    List<TrainUser> getTrainUserDetails();
    
    public Map<String,String> updateTrainUserDetails(String userId, TrainUserModel trainUserModel);
    
    
}
