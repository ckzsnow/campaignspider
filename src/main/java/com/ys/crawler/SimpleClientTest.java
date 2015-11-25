package com.ys.crawler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"file:D:/learngit/newcampaignspider/campaignspider/src/main/webapp/WEB-INF/dispatcher-servlet.xml"}) 
public class SimpleClientTest {
	ApplicationContext ct = null;
//	@Before
//	public void setUp() throws Exception {
//		ct = new FileSystemXmlApplicationContext("D:/learngit/newcampaignspider/campaignspider/src/main/webapp/WEB-INF/dispatcher-servlet.xml");
//	}

	@Test
	public void test() {
		SimpleClient sc=new SimpleClient();
		sc.getDates();
	}

}
