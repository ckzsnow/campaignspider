package com.ys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.constant.ConstantCode;
import com.ys.crawler.HuoDongXingCrawler;
import com.ys.dao.TrainUserMapper;
import com.ys.model.TrainUser;
import com.ys.model.TrainUserModel;
import com.ys.service.ITrainUserService;
import com.ys.utils.GenerateTrainUserValue;
@Service("trainUserService")
public class TrainServiceImpl implements ITrainUserService {
    
    private  TrainUserMapper trainUserMapper ;
    private static final int FILE_QUEUE_SIZE = 6762;// 阻塞队列大小  
    private static List<TrainUser> list = new LinkedList<>();
    private static BlockingQueue<TrainUser> queue = new LinkedBlockingQueue<TrainUser>(FILE_QUEUE_SIZE);
    private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);
    @Autowired
    public TrainServiceImpl(TrainUserMapper trainUserMapper) {
        this.trainUserMapper = trainUserMapper;
        list = trainUserMapper.selectTrainUserDetails();
        setQueueValue(list);
    }
    public void setQueueValue(List<TrainUser> list) {
        for (int i = 0; i < list.size(); i++) {
            queue.offer(list.get(i));
        }
        logger.debug("Queue size : {} ", queue.size());
    }
    @Override
    public Map<String, String> insertTrainUserDetails(TrainUser trainUser) {
        // TODO Auto-generated method stub
        Map<String, String> retMap = new HashMap<>();
        boolean writeSuccess = true;
        if (trainUser == null ) {
            retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_CODE,
                    ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_MSG);
            return retMap;
        } 
           if((trainUserMapper.insertTrainUser(trainUser)) == 0) writeSuccess = false;
           if(writeSuccess) {
               retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_CODE,
                       ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_MSG);
           } else {
               retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_CODE,
                       ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_MSG);
           }
        return retMap;
    }
    @Override
    public List<TrainUser> getTrainUserDetails() {
        // TODO Auto-generated method stub
        List<TrainUser> retList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            retList.add(queue.poll());
        }
        logger.debug(" Throw poll() List<TrainUser> size : {}", retList.size() );
        return retList;
    }

    @Override
    public Map<String, String> updateTrainUserDetails(String userId, TrainUserModel trainUserModel) {
        logger.debug(" Request return TrainUserModel : {}", trainUserModel.toString());
        // TODO Auto-generated method stub
        TrainUser trainUser;
        Map<String, String> map = GenerateTrainUserValue.getModelValue(trainUserModel);
        Map<String, String> retMap = new HashMap<>();
        for (int i = 0; i < 10; i ++) {
            trainUser = new TrainUser();
            if (map.get("InputBtxt" + i) != null && map.get("InputBtxt" + i).length() > 0) {
                trainUser.setBoxTxt(map.get("InputBtxt" + i));
                trainUser.setId(Integer.valueOf(map.get("DataId" + i)));
                trainUser.setCheckStatus("1");
                trainUser.setUserId(userId);
              int booNum =  trainUserMapper.updateTrainUserBoxTxt(trainUser);
              if (booNum == 0) {
                  retMap.put("error_code_fail", "1");
              }
            } else {
                trainUser.setId(Integer.valueOf(map.get("DataId" + i)));
                trainUser.setCheckStatus("2");
                trainUser.setUserId(userId);
                int booNum =  trainUserMapper.updateTrainUserCheckStatus(trainUser);
                if (booNum == 0) {
                    retMap.put("error_code_fail", "1");
                }
            }
        }
        return retMap;
    }
    
}
