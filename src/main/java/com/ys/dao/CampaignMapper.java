package com.ys.dao;

import java.util.List;

import com.ys.interceptor.Page;
import com.ys.model.Campaign;

public interface CampaignMapper {
    int deleteByPrimaryKey(String id);

    int insert(Campaign record);

    int insertSelective(Campaign record);

    Campaign selectByPrimaryKey(String id);
    
    List<Campaign> selectByPage(Page<Campaign> page);

    int updateByPrimaryKeySelective(Campaign record);

    int updateByPrimaryKeyWithBLOBs(Campaign record);

    int updateByPrimaryKey(Campaign record);
    
    int writeDB(Campaign record);
}