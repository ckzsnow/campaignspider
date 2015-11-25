package com.ys.dao;

import com.ys.model.Campaign;

public interface CampaignMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Campaign record);

    int insertSelective(Campaign record);

    Campaign selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Campaign record);

    int updateByPrimaryKeyWithBLOBs(Campaign record);

    int updateByPrimaryKey(Campaign record);
}