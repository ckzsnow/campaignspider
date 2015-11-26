package com.ys.service.impl;

import java.util.HashMap;
import java.util.List;
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
	public Map<String, String> writeInformationDB(List<Campaign> list) {
		Map<String, String> retMap = new HashMap<>();
		for(int i=0;i<list.size();i++){
			String sql = "insert into campaignspider (act_name, act_time, act_destination, act_originator_image，act_originator，act_enroll_sum，act_interest_sum，act_snapshot，act_enroll)"
					+ " values (?,?,?,?,?,?,?,?,?)";
			int affectedRows = jdbcTemplate.update(sql, list.get(i).getActName(),list.get(i).getActTime(),list.get(i).getActDestination(),list.get(i).getActOriginatorImage(),
					list.get(i).getActOriginator(),list.get(i).getActEnrollSum(),list.get(i).getActInterestSum(),list.get(i).getActSnapshot(),list.get(i).getActEnroll());
			if(affectedRows>0){
				retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_CODE,ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_MSG);
			}else{
				retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_CODE, ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_MSG);
			}
		}
		return retMap;
	}

	

}
