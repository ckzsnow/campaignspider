package com.ys.crawler;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ys.dao.CampaignMapper;
import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
import com.ys.service.impl.CampaignServiceImpl;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml"}) 
public class HuoDongXingCrawlerTest {
	
	@Autowired
	HuoDongXingCrawler hdxc;

	@Autowired
	CampaignMapper cm;
	@Autowired
	ICampaignService ics;
	@Test
	public void test() {
		List<String> list=hdxc.generateTasksURL();
		List<Campaign>cList=hdxc.crawlTaskData(list);
		ics.writeInformationDB(cList);
		hdxc.externalMethod();
	}

}
