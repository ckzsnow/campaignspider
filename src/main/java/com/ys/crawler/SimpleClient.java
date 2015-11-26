package com.ys.crawler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
@Component
public class SimpleClient {
	@Autowired
	private ICampaignService campaignService ;
	
	public void parseUri(String date){
//		 	String url="http://www.huodongxing.com/eventlist?orderby=v&range=1&d=t2&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7";
		 	String url="http://www.huodongxing.com/eventlist?orderby=r&d=ts&date=DATE&tag=%E5%88%9B%E4%B8%9A&city=%E4%B8%8A%E6%B5%B7";
		 	url.replace("DATE", date);
		 	// 设置代理服务器地址和端口        
	      // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https   
		  HttpMethod method=new GetMethod(url);  
		//使用POST方法  
		  //HttpMethod method = new PostMethod("http://java.sun.com");  
		  int num=this.showInformation(method);
		  if(num==1){
			  System.out.println("----------------------------------");
		  }
		  else{
			  for(int i=0;i<num;i++){
				  url=url+"&page="+(i+2);
				  HttpMethod  method2=new GetMethod(url);
				  this.showInformation(method2);
			  }
		  }
	}
	public int showInformation(HttpMethod method ){
		Campaign campaign=new Campaign();
		HttpClient client = new HttpClient();   
	      try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	      //打印服务器返回的状态  
	       System.out.println(method.getStatusLine());  
	       Document doc = null;
		try {
			doc = Jsoup.parse(method.getResponseBodyAsString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       String title=doc.title();
			System.out.println("-------------------------------<"+title+">---------------------------------------");
			Elements lis=doc.select("[class=event-horizontal-list-new] >li");
			for(int i=0;i<lis.size();i++){
				//背景图片
				Element bgimg = lis.get(i).select(" a img[src]").first();  
				String imgString=bgimg.toString().substring(10,bgimg.toString().length()-9);
				//标题
				Elements newtitle = lis.get(i).select(" h3 a[title] ");   
				//作者
				Element author = lis.get(i).select("div[class=apply] a[href] ").last(); 
				//作者 头像
				Elements authorimg = lis.get(i).select("div[class=apply] a[href] img[src]");
				String authorImgString=
						authorimg.toString().substring(23, authorimg.toString().lastIndexOf("\" alt"));
				//Time
				String time = lis.get(i).select("div").get(0).text(); 
				//地址
				String locations = lis.get(i).select("div").get(1).text(); 
//				 //报名地址
				 Element applyHref = lis.get(i).select("div[class=apply] a[href] ").last(); 
				 String applyHrefString=applyHref.toString().substring(9, applyHref.toString().lastIndexOf("\"><img"));
		        System.out.println("背景图片："+imgString);    
		        campaign.setActSnapshot(imgString);
				System.out.println("标题："+newtitle.text());
				campaign.setActName(newtitle.text());
				 System.out.println("作者："+author.text());
				 campaign.setActOriginator(author.text());
				 System.out.println("作者头像："+authorImgString);
				 campaign.setActOriginatorImage(authorImgString);
				 System.out.println("时间："+time);
				 campaign.setActTime(time);
				System.out.println("地址："+locations);
				campaign.setActDestination(locations);
				campaign.setActInterestSum(1);
				campaign.setActEnrollSum(1);
				System.out.println("报名地址：http://www.huodongxing.com/"+applyHrefString);
				campaign.setActEnroll("http://www.huodongxing.com/"+applyHrefString);
//				if(campaign!=null){
//					try{
//						campaignService.writeInformationDB(campaign);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//					
//				}
				
			}
			//判断当前信息有多少页
			Elements pages=doc.select("div[class=article] > div[class=pagination] >ul > li");
	      //释放连接  
	      method.releaseConnection();  
	      return pages.size()-2;
	
	}
	public static void main(String[] args) throws IOException {

		getDates();
	}
	public static  void getDates(){
		 
		   
//		   sc.parseUri();
			SimpleDateFormat df = new SimpleDateFormat("dd ");//设置日期格式
			SimpleDateFormat year = new SimpleDateFormat("yyyy-MM");
			String[] dates=year.format(new Date()).split("-");
			int dayNum=judgeDay(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
//			System.out.println(dayNum);
			int todayNum=Integer.parseInt(df.format(new Date()).trim());
//			System.out.println(todayNum);
			for(int i=todayNum;i<=dayNum;i++){
				System.out.println(Integer.parseInt(dates[0])+"----"+i);
				this.parseUri(Integer.parseInt(dates[0])+"-"+Integer.parseInt(dates[1])+"-"+i);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(int i=1;i<=todayNum;i++){
				System.out.println(Integer.parseInt(dates[0])+"----------"+(Integer.parseInt(dates[1])+1)+"--"+i);
				this.parseUri(Integer.parseInt(dates[0])+"-"+(Integer.parseInt(dates[1])+1)+"-"+i);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	   public  int judgeDay(int year, int month) {
	        Calendar c = Calendar.getInstance();
	        c.set(Calendar.DAY_OF_MONTH, 1); // 设置日期
	        c.set(Calendar.YEAR, year);
	        c.set(Calendar.MONTH, month - 1);
	        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	    }
}
