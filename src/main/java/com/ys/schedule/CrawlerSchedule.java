package com.ys.schedule;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ys.crawler.HuoDongBaCrawler;
import com.ys.crawler.HuoDongXingCrawler;
import com.ys.crawler.PostRequestCrawler;
import com.ys.utils.CommonUtils;

@Component
public class CrawlerSchedule {

	@Autowired
	private HuoDongBaCrawler huoDongBaCrawler;
	
	@Autowired
	private HuoDongXingCrawler huoDongXingCrawler;
	
	@Autowired
	private PostRequestCrawler postRequestCrawler;
	
	private List<String> dateList;
	
	private Random random;
	
	private static final Logger logger = LoggerFactory.getLogger(CrawlerSchedule.class);
	
	public CrawlerSchedule() {
		dateList = CommonUtils.generateDateList(30);
		random = new Random();
	}
	
	@Scheduled(fixedDelay= 1000 * 60 * 60 * 4)
	public void invoiceApprovalNotify() {
		while(dateList.size() != 0) {
			int sleepMinute = random.nextInt(5)%(5-3+1) + 3;
			int dateIndex = random.nextInt(dateList.size() - 1)%(dateList.size());
			logger.debug("schedule the huoDongXingCrawler, date={}, sleep={}", dateList.get(dateIndex), sleepMinute);
			huoDongXingCrawler.executeCrawl(dateList.remove(dateIndex));
			huoDongBaCrawler.executeCrawl(dateList.remove(dateIndex));
			try {
				Thread.sleep(sleepMinute * 60 * 1000);
			} catch (InterruptedException e) {
				logger.debug(e.toString());
			}
		}
		dateList = CommonUtils.generateDateList(30);
	}
	@Scheduled(fixedDelay= 1000 * 60 * 60 * 24 *40)
	public void campaignspiCompany() {
		postRequestCrawler.executeCrawl();
	}
}
