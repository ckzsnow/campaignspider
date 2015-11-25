package com.ys.service;

import java.util.Map;

import com.ys.model.Campaign;

public interface ICampaignService {

	public Map<String,String> writeInformationDB(Campaign campaign);
}
