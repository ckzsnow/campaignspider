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
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.ys.model.Campaign;
import com.ys.service.ICampaignService;
import com.ys.service.impl.CampaignServiceImpl;
import com.ys.utils.CommonUtils;
@Component
public class HuoDongBaCrawler {
	
	private static final Logger logger = LoggerFactory.getLogger(HuoDongBaCrawler.class);
	
	public static final String SEED_URL = "http://www.hdb.com/find/shanghai-flu-fy0-pPAGE?start_time=DATE";
	
	@Autowired
	private ICampaignService campaignService ;

	public  List<Campaign> crawlTaskData(String date){
 		int pageNum=1;
		int pageNo=1;
		List<Campaign> campaignList=new ArrayList<>();
		HttpClient client = new HttpClient(); 
		Document doc = null;
		while(pageNo<=pageNum){
			// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https   
			  try {
				String newUrl=SEED_URL.replace("PAGE",String.valueOf(pageNo)).replace("DATE", date);
				  HttpMethod method=new GetMethod(newUrl);
					  client.executeMethod(method);
				 //打印服务器返回的状态  
			    System.out.println(method.getStatusLine());
			    doc = Jsoup.parse(method.getResponseBodyAsString());
			    String title=doc.title();
			    System.out.println("----"+title+"----");
			    //judge pageNum
			    Elements lis=doc.select("div[class=find_outside] div[class=find_main] div[class=join_feny] >a");
			    if(doc.getAllElements().text().indexOf("上一页")!=-1){
			    	pageNum=lis.size()-1;
			    }else if(doc.getAllElements().text().indexOf("下一页")!=-1){
			    	pageNum=lis.size()-1;
			    }
			    Elements liNum=doc.select("div[class=find_outside] div[class=find_main] ul[class=find_main_ul] >li");
				for(int j=0;j<liNum.size();j++){
					System.out.println("-------------------------------------");
					Campaign campaign=new Campaign();
					//背景图片
					String img1=liNum.get(j).select("a img[src] ").first().toString();
					String img2=img1.substring(img1.indexOf("data-src=\"")+10,img1.lastIndexOf("\" src="));
					 campaign.setActSnapshot(img2);
					//标题
					String newtitle =liNum.get(j).select("div[class=find_main_div] div[class=find_main_title] >a ").text(); 
					campaign.setActName(newtitle);
					//作者
					String author = liNum.get(j).select("div[class=find_main_b] div[class=find_main_b_l] > a ").text();
					campaign.setActOriginator(author);
					//作者 头像
					String authorimg = liNum.get(j).select("div[class=find_main_b] div[class=find_main_b_l] >a img[src]").toString();
					String authorImgString=
							authorimg.substring(authorimg.indexOf("src=\"")+5,authorimg.lastIndexOf("g")+1);
					 campaign.setActOriginatorImage(authorImgString);
					 //Time
					String time = liNum.get(j).select("div[class=find_main_div] div[class=find_main_fixH] div[class=find_main_time]").text(); 
					campaign.setActTime(time);
					//地址
					String locations = liNum.get(j).select("div[class=find_main_div] div[class=find_main_fixH] div[class=find_main_address]").text(); 
					campaign.setActDestination(locations);
					//报名人数
					String texts = liNum.get(j).select("div[class=find_main_div] div[class=find_main_b] div[class=find_main_b_r] span[class=find_hd_join_num]").text();
					String[] middleTexts=texts.split(" ");
					try{
						campaign.setActEnrollSum(Integer.parseInt(middleTexts[0]));
					}catch(Exception e){
						campaign.setActEnrollSum(0);
					}
					
					String collections = liNum.get(j).select("div[class=find_main_div] div[class=find_main_b] div[class=find_main_b_r] >p span[class=find_hd_read_num]").text();
					//收藏人数
					try{
						if(collections.indexOf(",")!=-1){
							campaign.setActInterestSum(Integer.parseInt(collections.replace(",","")));
						}else{
							campaign.setActInterestSum(Integer.parseInt(collections));
						}
					}catch(Exception e){
						campaign.setActInterestSum(0);
					}
					
//					 //报名地址
					 String applyHref = liNum.get(j).select("a[href]").first().toString(); 
					 String applyHrefString=applyHref.substring(9, applyHref.lastIndexOf(".html")+5);
					 campaign.setActEnroll(applyHrefString);
					 String text=crawlTaskDetailData(applyHrefString);
					 campaign.setActDetails(text);
					 campaign.setId(CommonUtils.generateMD5(newtitle));
					 campaignList.add(campaign);
				}// for loop
				pageNo++;
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//while
		System.out.println("Get Detail num-------------"+campaignList.size());
		
		return campaignList;
	}
	//get detail by apply link
	public  String crawlTaskDetailData(String detailURL){
		HttpMethod method=new GetMethod(detailURL); 
		HttpClient client = new HttpClient(); 
		Document doc = null;
		String lis="";
		try {
			client.executeMethod(method);
			//打印服务器返回的状态  
		    doc = Jsoup.parse(method.getResponseBodyAsString());
		    lis=doc.select("div[class=container] div[class=content] div[class=content-body] div[class=detail_time_attr_det_con]  div[class=dt_content]").html();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lis;
	}

	public  void executeCrawl(String date){
		List<Campaign>cList=crawlTaskData(date);
		if(cList.size()!=0){
			campaignService.writeInformationDB(cList);
		}else{
			System.out.println("查询不到信息");
		}
	}
}
