package com.ys.crawler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:dispatcher-servlet.xml"}) 
public class SimpleClientTest {
	
	@Autowired
	SimpleClient sc;

	@Test
	public void test() {
		sc.getDates();
	}

}