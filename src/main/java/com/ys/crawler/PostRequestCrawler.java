package com.ys.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ys.model.Campaign;
import com.ys.model.Company;
import com.ys.service.ICompanyService;
@Component
public class PostRequestCrawler {
	@Autowired
	private ICompanyService companyService ;
		public static void main(String[] args) {
			PostRequestCrawler prc =new PostRequestCrawler();
			prc.mainTest("生一投资管理有限公司金沙江西路店");
			System.exit(0);
		}
	
	public void entranceMethod(){
		List<String> conditionsList = new ArrayList<>();
		try {
			conditionsList = readFileByCharteArray();
			for(String s:conditionsList){
				 mainTest(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
public  List<String> readFileByCharteArray() throws IOException {
	List <String> list =new ArrayList<>();
	String filePath = PostRequestCrawler.class.getClassLoader().getResource("writerCharacters.txt").getPath();
	String newpath = filePath.substring(1,filePath.length());
//		String readFilePath =System.getProperty("catalina.home") + File.separator + "writerCharacters.txt";
	InputStreamReader isr = new InputStreamReader(new FileInputStream(newpath), "UTF-8");
	BufferedReader br = new BufferedReader(isr);
		String str= new String();
		try{
			while((str = br.readLine().trim())!=null){ 
				list.add(str);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
		br.close();
		return list;
		}
	public Map<String, String> mainTest(String conditions) {
		Map<String, String> retMap =new HashMap<>();
		Company company = new Company();
		String url="http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch";
		//set postRequest body info
		NameValuePair[] data = { new NameValuePair("searchType", "1"), new NameValuePair("keyWords", "上海"+conditions) };
		String nums = getPageNums(url,data);
		if(nums == null || nums.isEmpty()){
			return null;
		}
		for(int i = 1; i<= Integer.parseInt(nums); i++ ) {
			List<Map<String, String>> tableList = new ArrayList<>();
			List<String> idList = new ArrayList<>();
			NameValuePair[] newData = { new NameValuePair("searchType", "1"), new NameValuePair("keyWords", "上海"+conditions), new NameValuePair("pageNo",String.valueOf(i)) };
			Map<String,String> infoMap = getResponseBody(url , newData);
			  for (String key : infoMap.keySet()) {
				  if(key.equals("1")) {
					  tableList =   parseResponseIsEstablishment(infoMap.get("1"));
					 if(tableList!=null){
						 idList =   judgeDateWhetherAccord(tableList);
						 String responseBody =new String();
						  if(idList.size()!=0){
							 for(int x = 0;x<idList.size(); x++){
								 responseBody	  =  getCompanyDetails("http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail",idList.get(x));  
								 company =  getCompanyDetail(responseBody);
								 retMap =  companyService.writeInformationDB(company);
							 }
						  }
					 }
				  }else{
					  System.out.println("没有信息");
				  }
			 }
		}
		return retMap;
	}
	// parse return bodyDetails
	public  Company getCompanyDetail(String responseBody){
		Company company = new Company();
		Document doc = Jsoup.parse(responseBody);
		Elements tdElements=doc.select("table[class = list_boder] > tbody > tr >td");
		Map<String,String> elementMap = new HashMap<>();
		for(int i = 0;i<tdElements.size();i=i+2){
			elementMap.put(tdElements.get(i).text().replace(":", ""),tdElements.get(i+1).text());
		}
				company.setcName(elementMap.containsKey("名称") ? elementMap.get("名称") : "无");
				if(company.getcName().equals("无")){
					company.setcName(elementMap.containsKey("企业名称") ? elementMap.get("企业名称") : "无");
				}
				
				company.setRegisterNo(elementMap.containsKey("注册号") ? Long.parseLong(elementMap.get("注册号")) : 0);
				company.setRepresentativeName( elementMap.containsKey("法定代表人姓名")  ? elementMap.get("法定代表人姓名")  : "无" );
				if(company.getRepresentativeName().equals("无")){
					company.setRepresentativeName(  elementMap.containsKey("负责人")  ?  (elementMap.get("负责人" ) ) : "无" );
				}else if ( company.getRepresentativeName().equals("无")){
					company.setRepresentativeName(  elementMap.containsKey("投资人姓名")  ?  (elementMap.get("投资人姓名" ) ) : "无" );
				}else if( company.getRepresentativeName().equals("无") ){
					company.setRepresentativeName(  elementMap.containsKey("执行事务合伙人")  ?  (elementMap.get("执行事务合伙人" ) ) : "无" );
				}
				company.setCompanyLocation( elementMap.containsKey("住所") ? elementMap.get("住所") : "无");
				if( company.getCompanyLocation().equals("无")){
					company.setCompanyLocation(  elementMap.containsKey("营业场所")  ? elementMap.get("营业场所") : "无");
				} else if( company.getCompanyLocation().equals("无")){
					company.setCompanyLocation(  elementMap.containsKey("企业住所")  ? elementMap.get("企业住所") : "无");
				}else if( company.getCompanyLocation().equals("无")){
					company.setCompanyLocation(  elementMap.containsKey("主要经营场所")  ? elementMap.get("主要经营场所") : "无");
				}
				company.setRegisterMoney(elementMap.containsKey("注册资本") ? elementMap.get("注册资本") : "无");
				company.setState(elementMap.containsKey("企业状态") ? elementMap.get("企业状态") : "无");
				company.setCompanyType(elementMap.containsKey("公司类型") ? elementMap.get("公司类型") : "无");
				if(company.getCompanyType().equals("无")){
					company.setCompanyType(elementMap.containsKey("企业类型") ? elementMap.get("企业类型") : "无");
				} else if(company.getCompanyType().equals("无")){
					company.setCompanyType(elementMap.containsKey("合伙企业类型") ? elementMap.get("合伙企业类型") : "无");
				}
				company.setBuildDate(elementMap.containsKey("成立日期") ? elementMap.get("成立日期") : "无");
				company.setDeadline(elementMap.containsKey("营业期限") ? elementMap.get("营业期限") : "无");
				company.setRegisterLocation(elementMap.containsKey("登记机关") ? elementMap.get("登记机关") : "无");
				if( company.getRegisterLocation() .equals( "无")){
					company.setRegisterLocation(elementMap.containsKey("发照机关") ? elementMap.get("发照机关") : "无");
				}
				company.setAcceptLocation(elementMap.containsKey("受理机关") ? elementMap.get("受理机关") : "无");
				company.setOperateScope(elementMap.containsKey("经营范围") ? elementMap.get("经营范围") : "无");
				if( company.getOperateScope().equals("无")){
					company.setOperateScope(elementMap.containsKey("经营范围及方式") ? elementMap.get("经营范围及方式") : "无");
				}
		
		return company;
		
	}
	public static String getCompanyDetails(String url,String id){
		HttpClient httpClient = new HttpClient();
		 String response = new String();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Referer"," http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail");
		postMethod.setRequestHeader("Origin: "," http://www.sgs.gov.cn");
		postMethod.setRequestHeader("Upgrade-Insecure-Requests"," 1");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"GBK");
		NameValuePair[] data = { new NameValuePair("etpsId", id) }; 
		postMethod.setRequestBody(data);
		 try {
			 httpClient.executeMethod(postMethod);
			 response =  new String(postMethod.getResponseBodyAsString().getBytes("GBK"));
		        System.out.println("~~~"+response);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return response;
		
	}
	public static  List<String> judgeDateWhetherAccord(List<Map<String, String>> tableList){
		List<Map<String,String>> save = new ArrayList<>();  
		 for (Map<String, String> m : tableList)  {  
		      for (String k : m.keySet() ) {  
		        System.out.println(k + " : " + m.get(k)+"-------"+m.get(k).substring(8, 16)); 
		        System.out.println(m.get(k).substring(8, 16));
		        if(Integer.parseInt(m.get(k).substring(8, 16)) < 20150801 ){
		        	save.add(m);
		        }
		      }  
		    }
		 tableList.removeAll(save);
		 List<String> list = new ArrayList<>();
		  for (Map<String, String> m : tableList)  
		    {  
		      for (String k : m.keySet())  
		      {  
		    	  list.add(m.get(k));
		      }
		      }  
		 return list;
	}
	public static String getPageNums(String url,NameValuePair[] data){
		String nums = new String();
		try{
			Map<String, String> map = getResponseBody(url,data);
			  for (String key : map.keySet()) {
				  if(key.equals("1")) {
					  nums=map.get(key).substring(map.get(key).indexOf( "				共")+5, map.get(key).indexOf("页"));
					System.out.println(); 
				  }
			 }
		}catch( Exception e){
			e.printStackTrace();
			return "1";
		}
		
		return nums;
		
	}
	
	
	public static Map<String,String> getResponseBody(String url,NameValuePair[] datas ){
		HttpClient httpClient = new HttpClient();
		Map<String,String> retMap = new HashMap<>();
		PostMethod postMethod = new PostMethod(url);
		// response charset = UTF-8
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		//bodyRequest info add to postMethod
		postMethod.setRequestBody(datas);
		try { 
			//add postMethod to httpClient
			httpClient.executeMethod(postMethod);
			//Returns the response body of the HTTP method to byte[]
			String response =  new String(postMethod.getResponseBodyAsString().getBytes("GBK"));
			System.out.println(response);
			//check return response body whether hava null or hava details
			 if(response.indexOf("未找到符合")==-1){
				 // hava details 
				 retMap.put("1",response);
				 System.out.println(retMap.get("1"));
//				 System.out.println("response:"+response);
			 }else{
				 retMap.put("0","postRequest return null !!");
			 }
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retMap;
	}
	// judge company whether estavblishment
	public static List<Map<String, String>> parseResponseIsEstablishment(String bodyStrings){
		List<Map<String,String>> infoList = new ArrayList<>();
		
		//parse return body
		Document doc = Jsoup.parse(bodyStrings);
		Elements tablesElements=doc.select("table[class = list] table[class = con] ");
		for(Element elements:tablesElements) {
			if(elements.text().indexOf("确立") != -1) {
				Map<String,String> infoMap = new HashMap<>();
//				System.out.println( "---------"+elements.select(" tbody > tr > td "));
			Elements ele =	elements.select(" tbody > tr > td > a ");
			String onclickTxt = ele.attr("onclick");
//				System.out.println(onclickTxt);
				String id = onclickTxt.substring(onclickTxt.indexOf("('")+2 ,onclickTxt.lastIndexOf("')"));
				infoMap.put("id",id);
				infoList.add(infoMap);
			}
			
		}
		if(infoList.size() == 0) {
			return null ;
		}else{
			return infoList;
		}
	}
	public  void executeCrawl(){
		try{
			this.entranceMethod();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("查询异常");
		}
		
	}
	
}
