package com.ys.dao;

import java.util.List;
import java.util.Queue;

import com.ys.model.TrainUser;

public interface TrainUserMapper {
    
    List<TrainUser> selectTrainUserDetails();

    int insertTrainUser(TrainUser trainUser);

    int updateTrainUserBoxTxt(TrainUser trainUser);
    
    int updateTrainUserCheckStatus(TrainUser trainUser);
}
