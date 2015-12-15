package com.ys.crawler;

import java.io.IOException;
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

public class PostRequest {
	
	public static void main(String[] args) {
		List<Map<String, String>> tableList = new ArrayList<>();
		String url="http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch";
		//set postRequest body info
		NameValuePair[] data = { new NameValuePair("searchType", "1"), new NameValuePair("keyWords", "上海元升") };
		String nums = getPageNums(url,data);
		for(int i = 1; i<= Integer.parseInt(nums); i++ ) {
			NameValuePair[] newData = { new NameValuePair("searchType", "1"), new NameValuePair("keyWords", "上海元升"), new NameValuePair("pageNo",String.valueOf(i)) };
			Map<String,String> infoMap = getResponseBody(url , newData);
			  for (String key : infoMap.keySet()) {
				  if(key.equals("1")) {
					  tableList =   parseResponseIsEstablishment(infoMap.get("1"));
					  tableList =   judgeDateWhetherAccord(tableList);
					  String responseBody =  getCompanyDetails("http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail");
					  getCompanyDetail(responseBody);
				  }else{
					  System.out.println("没有信息");
				  }
				  
			 }
			
		}
	}
	// parse return bodyDetails
	public static CompanyModel getCompanyDetail(String responseBody){
		CompanyModel company = new CompanyModel();
		Document doc = Jsoup.parse(responseBody);
		Elements tablesElements=doc.select("table[class = list_boder] > tbody > tr");
		int i=1;
			for(Element elements:tablesElements) {
				if(i == 1){
					company.setcName(elements.select("td[class=list_td_1]").first().text());
					company.setRegisterNo(Long.parseLong(elements.select("td[class=list_td_1]").last().text()));
				}else if(i == 2){
					company.setRepresentativeName(elements.select("td[class=list_td_1]").first().text());
					company.setCompanyLocation(elements.select("td[class=list_td_1]").last().text());
				}else if(i == 3){
					company.setRegisterMoney(elements.select("td[class=list_td_1]").text());
				}	else if(i == 4){
					company.setState(elements.select("td[class=list_td_1]").first().text());
					company.setCompanyType(elements.select("td[class=list_td_1]").last().text());
				}	else if(i == 5){
					company.setBuildDate(elements.select("td[class=list_td_1]").first().text());
					company.setDeadline(elements.select("td[class=list_td_1]").last().text());
				}	else if(i == 6){
					company.setRegisterLocation(elements.select("td[class=list_td_1]").first().text());
					company.setAcceptLocation(elements.select("td[class=list_td_1]").last().text());
				}	else if(i == 7){
					company.setOperateScope(elements.select("td[class=list_td_1]").text());
				}	
				i++;
		}
		return company;
		
	}
	public static String getCompanyDetails(String url){
		HttpClient httpClient = new HttpClient();
		 String response = new String();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Referer"," http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail");
		postMethod.setRequestHeader("Origin: "," http://www.sgs.gov.cn");
		postMethod.setRequestHeader("Upgrade-Insecure-Requests"," 1");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		NameValuePair[] data = { new NameValuePair("etpsId", "100000032015060100069") }; 
		postMethod.setRequestBody(data);
		 try {
			 httpClient.executeMethod(postMethod);
			 response =  new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
		        System.out.println("~~~"+response);
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		return response;
		
	}
	public static  List<Map<String, String>> judgeDateWhetherAccord(List<Map<String, String>> tableList){
		List<Map<String,String>> save = new ArrayList<>();  
		 for (Map<String, String> m : tableList)  {  
		      for (String k : m.keySet() ) {  
		        System.out.println(k + " : " + m.get(k)+"-------"+m.get(k).substring(8, 16)); 
		        if(Integer.parseInt(m.get(k).substring(8, 16)) < 20150101 ){
//		        	tableList.remove(m);
		        	
		        	save.add(m);
		        }
		      }  
		  
		    }
		 tableList.removeAll(save);
		 return tableList;
	}
	public static String getPageNums(String url,NameValuePair[] data){
		String nums = new String();
		Map<String, String> map = getResponseBody(url,data);
		  for (String key : map.keySet()) {
			  if(key.equals("1")) {
				  nums=map.get(key).substring(map.get(key).indexOf( "				共")+5, map.get(key).indexOf("页"));
				System.out.println(); 
			  }
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
			String response =  new String(postMethod.getResponseBodyAsString().getBytes("UTF-8"));
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
			Map<String,String> infoMap = new HashMap<>();
			infoMap.put("0","getbody info is  nul");
			infoList.add(infoMap);
			return infoList ;
		}else{
			return infoList;
		}
	}
}
