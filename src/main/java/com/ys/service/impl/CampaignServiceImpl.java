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
	private CampaignMapper campaignMapper;

	@Override
	public Map<String, String> writeInformationDB(List<Campaign> campaignList) {
		Map<String, String> retMap = new HashMap<>();
		boolean writeSuccess = true;
		if(campaignList == null || campaignList.size() == 0) {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_EMPTY_ERROR_MSG);
			return retMap;
		}
		for (Campaign campaign : campaignList) {
			Campaign oldCampaign = campaignMapper.selectByPrimaryKey(campaign.getId());
			if(oldCampaign != null) continue;
			if(campaignMapper.insert(campaign) == 0) writeSuccess = false;
		}
		if(writeSuccess) {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_SUCCESS_MSG);
		} else {
			retMap.put(ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_CODE,
					ConstantCode.CAMPAIGN_SERVICE_WRITE_DATABASE_WRITE_ERROR_MSG);
		}
		return retMap;
	}

}
