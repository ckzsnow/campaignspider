package com.ys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ys.constant.ConstantCode;
import com.ys.dao.CampaignMapper;
import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
@Service("campaignService")
public class CampaignServiceImpl implements ICampaignService {

	 @Autowired
	  private	CampaignMapper campaignMapper;
	@Override
	public Map<String, String> writeInformationDB(List<Campaign> list) {
		String resource = "CampaignMapper.xml";
		Map<String, String> retMap = new HashMap<>();
		for(int i=0;i<list.size();i++){
			int affectedRows=campaignMapper.insert(list.get(i));
			if(affectedRows>0){
				retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_CODE,ConstantCode.USER_MODIFY_PWD_DATABASE_SUCCESS_MSG);
			}else{
				retMap.put(ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_CODE, ConstantCode.USER_MODIFY_PWD_DATABASE_OPERATE_ERROR_ERROR_MSG);
			}
		}
		return retMap;
	}

	

}
