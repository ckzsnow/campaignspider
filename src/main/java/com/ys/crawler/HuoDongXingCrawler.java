package com.ys.crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
import com.ys.utils.CommonUtils;

@Component
public class HuoDongXingCrawler {

	public static final String SEED_URL = "http://www.huodongxing.com/eventlist?orderby=n&range=1&d=ts&date=DATE&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7&page=PAGE";

	private static final Logger logger = LoggerFactory.getLogger(HuoDongXingCrawler.class);

	@Autowired
	private ICampaignService campaignService;

	private HttpClient httpClient = new HttpClient();

	public void executeCrawl(String date) {
		String taskUrl = SEED_URL.replace("DATE", date);
		List<Campaign> campaignList = crawlTaskData(taskUrl);
		campaignService.writeInformationDB(campaignList);
	}

	private String crawlTaskDetailData(String detailURL) {
		HttpMethod method = new GetMethod(detailURL);
		String actDetailInfo = "";
		try {
			httpClient.executeMethod(method);
			logger.debug("HTTP status : {}", method.getStatusLine().toString());
			Document doc = Jsoup.parse(method.getResponseBodyAsString());
			actDetailInfo = doc.select("div[id=container-lg] div[class=article] div[id=eventContentAreaMain]").html();
		} catch (HttpException e) {
			logger.debug(e.toString());
		} catch (IOException e) {
			logger.debug(e.toString());
		}
		return actDetailInfo;
	}

	private List<Campaign> crawlTaskData(String taskUrl) {
		int pageNum = 1;
		List<Campaign> campaignList = new ArrayList<>();
		while (true) {
			try {
				String url = taskUrl.replace("PAGE", String.valueOf(pageNum));
				HttpMethod method = new GetMethod(url);
				httpClient.executeMethod(method);
				logger.debug(method.getStatusLine().toString());
				Document doc = Jsoup.parse(method.getResponseBodyAsString());
				String title = doc.title();
				logger.debug("title : {}", title);
				if (doc.getAllElements().text().indexOf("找不到活动，请修改查询范围") != -1) {
					logger.debug("current document has no datas!");
					break;
				}
				pageNum++;
				Elements liElements = doc.select("[class=event-horizontal-list-new] >li");
				for (Element liElement : liElements) {
					Campaign campaign = new Campaign();
					Element bgimg = liElement.select(" a img[src]").first();
					String imgString = bgimg.toString().substring(10, bgimg.toString().length() - 9);
					Elements newtitle = liElement.select(" h3 a[title] ");
					Element author = liElement.select("div[class=apply] a[href] ").last();
					Elements authorimg = liElement.select("div[class=apply] a[href] img[src]");
					String authorImgString = authorimg.toString().substring(23,
							authorimg.toString().lastIndexOf("\" alt"));
					String time = liElement.select("div").get(0).text();
					String locations = liElement.select("div").get(1).text();
					String texts = liElement.select("div").get(2).text();
					String[] middleTexts = texts.split(" ");
					String[] middleTexts2 = middleTexts[1].split("\\|");
					Element applyHref = liElement.select("div[class=apply] a[href] ").last();
					String applyHrefString = "http://www.huodongxing.com/"
							+ applyHref.toString().substring(9, applyHref.toString().lastIndexOf("\"><img"));
					String taskDetail = crawlTaskDetailData(applyHrefString);
					campaign.setActSnapshot(imgString);
					campaign.setActName(newtitle.text());
					campaign.setActOriginator(author.text());
					campaign.setActOriginatorImage(authorImgString);
					campaign.setActTime(time);
					campaign.setActDestination(locations);
					campaign.setActEnrollSum(Integer.parseInt(middleTexts2[0]));
					campaign.setActInterestSum(Integer.parseInt(middleTexts2[1]));
					campaign.setActEnroll(applyHrefString);
					campaign.setActDetails(taskDetail);
					campaign.setId(CommonUtils.generateMD5(newtitle.text()));
					campaignList.add(campaign);
				}
				Thread.sleep(2000);
			} catch (HttpException e) {
				logger.debug(e.toString());
			} catch (IOException e) {
				logger.debug(e.toString());
			} catch (InterruptedException e) {
				logger.debug(e.toString());
			}
		}
		logger.debug("campaignList size : {}", campaignList.size());
		return campaignList;
	}
}