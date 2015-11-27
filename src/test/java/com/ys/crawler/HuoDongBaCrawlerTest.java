package com.ys.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml"}) 
public class HuoDongBaCrawlerTest {
	@Autowired
	HuoDongBaCrawler hdbc;
	@Test
	public void test() {
		hdbc.executeCrawl("2015-12-01");
	}
}
