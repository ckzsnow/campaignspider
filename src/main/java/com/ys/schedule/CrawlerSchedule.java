package com.ys.schedule;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ys.crawler.HuoDongXingCrawler;
import com.ys.utils.CommonUtils;

@Component
public class CrawlerSchedule {

	@Autowired
	private HuoDongXingCrawler huoDongXingCrawler;
	
	private List<String> dateList;
	
	private Random random;
	
	private static final Logger logger = LoggerFactory.getLogger(CrawlerSchedule.class);
	
	public CrawlerSchedule() {
		dateList = CommonUtils.generateDateList(30);
		random = new Random();
	}
	
	@Scheduled(fixedDelay= 1000 * 60 * 60 * 2)
	public void invoiceApprovalNotify() {
		if(dateList.size() == 0) dateList = CommonUtils.generateDateList(30);
		int sleepMinute = random.nextInt(5)%(5-3+1) + 3;
		int dateIndex = random.nextInt(dateList.size() - 1)%(dateList.size());
		huoDongXingCrawler.executeCrawl(dateList.remove(dateIndex));
		try {
			Thread.sleep(sleepMinute * 60 * 6000);
		} catch (InterruptedException e) {
			logger.debug(e.toString());
		}
	}
	
}
