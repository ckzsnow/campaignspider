package com.ys.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.ys.constant.ConstantCode;
import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
@Service("campaignService")
public class CampaignServiceImpl implements ICampaignService {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public Map<String, String> writeInformationDB(Campaign campaign) {
		Map<String, String> retMap = new HashMap<>();
		String sql = "insert into campaignspider (act_name, act_time, act_destination, act_originator_image，act_originator，act_enroll_sum，act_interest_sum，act_snapshot，act_enroll)"
				+ " values (?, ?, ?, ?)";
		int affectedRows = jdbcTemplate.update(sql, campaign.getActName(),campaign.getActTime(),campaign.getActDestination(),campaign.getActOriginatorImage(),
				campaign.getActOriginator(),campaign.getActEnrollSum(),campaign.getActInterestSum(),campaign.getActSnapshot(),campaign.getActEnroll());
		if(affectedRows>0){
			retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_CODE,ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_MSG);
		}else{
			retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_CODE, ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_MSG);
		}
		return retMap;
	}


}
