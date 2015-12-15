package com.ys.crawler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class PostTest {

	public static  void postRequest(String args) {
		HttpClient httpClient = new HttpClient();
		
		String url = "http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch" ;
		PostMethod postMethod = new PostMethod(url);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		NameValuePair[] data = { new NameValuePair("searchType", "1"), new NameValuePair("keyWords", "上海"+args), new NameValuePair("period", "上海"+args),new NameValuePair("pageNo", "2") }; 
		
		postMethod.setRequestBody(data);
		 try {
			 httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
//			System.out.println(new String(responseBody));
			 String response =  new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
//			 System.out.println("getStatusLine:"+postMethod.getStatusLine());
//		        System.out.println("~~~"+postMethod.getResponseBodyAsString());
			 if(response.indexOf("未找到符合")==-1){
				 System.out.println("response:"+response);
			 }
			
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 postMethod.releaseConnection();
	}
	
	public static void getInforPostMethod(){
HttpClient httpClient = new HttpClient();
		
		String url = "http://www.sgs.gov.cn/lz/etpsInfo.do?method=viewDetail" ;
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Referer"," http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch");
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"UTF-8");
		NameValuePair[] data = { new NameValuePair("etpsId", "140000032005062700955") }; 
		postMethod.setRequestBody(data);
		 try {
			 httpClient.executeMethod(postMethod);
			byte[] responseBody = postMethod.getResponseBody();
//			System.out.println(new String(responseBody));
			 String response =  new String(postMethod.getResponseBodyAsString().getBytes("utf-8"));
//			 System.out.println("getStatusLine:"+postMethod.getStatusLine());
//		        System.out.println("~~~"+postMethod.getResponseBodyAsString());
			 if(response.indexOf("未找到符合")==-1){
				 System.out.println("response:"+response);
			 }
			
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		 postMethod.releaseConnection();
		 
//		 class Test{
//			 public void main(String[] args) {
//				 getInforPostMethod();
//			}
//			 
//		 }
	}
	
	public static void main(String[] args) {
//		getInforPostMethod();
		postRequest("元升");
	}

}
