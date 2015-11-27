package com.ys.crawler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
import com.ys.service.impl.CampaignServiceImpl;
@Component
public class HuoDongXingCrawler {
	@Autowired
	private ICampaignService campaignService ;
	
	public static List<String> generateTasksURL(){
		List<String>list=new ArrayList<String>();
		//设置日期格式
		SimpleDateFormat day = new SimpleDateFormat("dd ");
		SimpleDateFormat yearAndMonth = new SimpleDateFormat("yyyy-MM");
		String[] dates=yearAndMonth.format(new Date()).split("-");
		int dayNum=judgeDay(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
		int todayNum=Integer.parseInt(day.format(new Date()).trim());
		for(int i=todayNum;i<=dayNum;i++){
			String url1=Integer.parseInt(dates[0])+"-"+dates[1]+"-"+i;
			System.out.println(url1);
			String newUrl1="http://www.huodongxing.com/eventlist?orderby=r&d=ts&date=DATE&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7&page=PAGE";
			String nu2=newUrl1.replace("DATE", url1);
			list.add(nu2);

		}
		for(int i=1;i<=todayNum;i++){
			String url2=Integer.parseInt(dates[0])+"-"+(Integer.parseInt(dates[1])+1)+"-"+i;
			System.out.println(url2);
			String newUrl1="http://www.huodongxing.com/eventlist?orderby=r&d=ts&date=DATE&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7&page=PAGE";
			String nu2=newUrl1.replace("DATE", url2);
			list.add(nu2);

		}
		return list;
	}
	public static List<Campaign> crawlTaskData(List<String> list){
		int pageNum=1;
		List<Campaign> campaignList=new ArrayList<>();
		
		HttpClient client = new HttpClient(); 
		Document doc = null;
		boolean bodyIsNull=true;
		while(bodyIsNull){
			
		for(int i=0;i<list.size();i++){
			// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https   
			  try {
				  String url=list.get(i).replace("PAGE",String.valueOf(pageNum));
//				  HttpMethod method=new GetMethod("http://www.huodongxing.com/eventlist?orderby=r&d=ts&date=2015-11-26&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7&page=1111"); 
				  HttpMethod method=new GetMethod(list.get(i));
				  client.executeMethod(method);
				 //打印服务器返回的状态  
			    System.out.println(method.getStatusLine());
			    doc = Jsoup.parse(method.getResponseBodyAsString());
			    String title=doc.title();
			    System.out.println("----"+title+"----");
			    if(doc.getAllElements().text().indexOf("找不到活动，请修改查询范围")==-1){
			    	pageNum++;
			    }else{
			    	//-----------------page noDeatil
			    	System.out.println("page No details");
			    	return campaignList;
			    }
			    Elements lis=doc.select("[class=event-horizontal-list-new] >li");

				for(int j=0;j<lis.size();j++){
					Campaign campaign=new Campaign();
					//背景图片
					Element bgimg = lis.get(j).select(" a img[src]").first();  
					String imgString=bgimg.toString().substring(10,bgimg.toString().length()-9);
					 campaign.setActSnapshot(imgString);
					//标题
					Elements newtitle = lis.get(j).select(" h3 a[title] "); 
					campaign.setActName(newtitle.text());
					//作者
					Element author = lis.get(j).select("div[class=apply] a[href] ").last();
					campaign.setActOriginator(author.text());
					//作者 头像
					Elements authorimg = lis.get(j).select("div[class=apply] a[href] img[src]");
					String authorImgString=
							authorimg.toString().substring(23, authorimg.toString().lastIndexOf("\" alt"));
					 campaign.setActOriginatorImage(authorImgString);
					//Time
					String time = lis.get(j).select("div").get(0).text(); 
					campaign.setActTime(time);
					//地址
					String locations = lis.get(j).select("div").get(1).text(); 
					campaign.setActDestination(locations);
					//收藏人数
					String texts = lis.get(j).select("div").get(2).text();
					String[] middleTexts=texts.split(" ");
					String[] middleTexts2=middleTexts[1].split("\\|");
					campaign.setActEnrollSum(Integer.parseInt(middleTexts2[0]));
					//报名人数
					campaign.setActInterestSum(Integer.parseInt(middleTexts2[1]));
//					 //报名地址
					 Element applyHref = lis.get(j).select("div[class=apply] a[href] ").last(); 
					 String applyHrefString=applyHref.toString().substring(9, applyHref.toString().lastIndexOf("\"><img"));
					 campaign.setActEnroll("http://www.huodongxing.com/"+applyHrefString);
					 String text=crawlTaskDetailData("http://www.huodongxing.com/"+applyHrefString);
					 campaign.setActDetails(text);
					 campaignList.add(campaign);
				}
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			  //--------------------------
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		}//while
		System.out.println("Get Detail num-------------"+campaignList.size());
		return campaignList;
	}
	//get detail by apply link
	public static String crawlTaskDetailData(String detailURL){
		HttpMethod method=new GetMethod(detailURL); 
		HttpClient client = new HttpClient(); 
		Document doc = null;
		String lis="";
		try {
			client.executeMethod(method);
			//打印服务器返回的状态  
		    System.out.println(method.getStatusLine());
		    doc = Jsoup.parse(method.getResponseBodyAsString());
		    lis=doc.select("div[id=container-lg] div[class=article] div[id=eventContentAreaMain]").text().toString();
		    System.out.println(lis);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lis;
	}
	private static  int judgeDay(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1); // 设置日期
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
	public static void externalMethod(){
		List<String> list=generateTasksURL();
		List<Campaign>cList=crawlTaskData(list);
		if(cList.size()!=0){
			ICampaignService ics=new CampaignServiceImpl();
			ics.writeInformationDB(cList);
		}else{
			System.out.println("查询不到信息");
		}
		
	}
	public static void main(String[] args) {
		externalMethod();
	}
}
