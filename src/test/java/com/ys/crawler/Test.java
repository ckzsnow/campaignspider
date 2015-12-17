package com.ys.crawler;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class Test {

	public static void main(String[] args) throws HttpException, IOException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod("http://www.sgs.gov.cn/lz/etpsInfo.do?method=doSearch");
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		RequestEntity entity = new StringRequestEntity("searchType=1&keyWords=%E4%B8%8A%E6%B5%B7%E5%85%83%E5%8D%87", "application/x-www-form-urlencoded", null);
		method.setRequestEntity(entity);
		client.executeMethod(method);
		System.out.println(method.getResponseBodyAsString());
	}
	
}
