package com.ys.crawler;

import java.util.Random;

public class CreateChineseCharacters {
	  public static String create() throws Exception {

	       String str = null;
	       int hightPos, lowPos; // 定义高低位
	       Random random = new Random();
	       hightPos = (176 + Math.abs(random.nextInt(39)));//获取高位值
	       lowPos = (161 + Math.abs(random.nextInt(93)));//获取低位值
	       byte[] b = new byte[2];
	       b[0] = (new Integer(hightPos).byteValue());
	       b[1] = (new Integer(lowPos).byteValue());
	       str = new String(b, "GBk");//转成中文
	       return str;
	    }
	  public static void main(String[] args) {
		  PostTest pt = new PostTest() ;
		  int i=0;
		  try {
			  StringBuilder charactersBuilder =new StringBuilder();
			  while(true){
				  i++;
				  charactersBuilder.append(create()) ;
				  if(i%2==0){
					  System.out.println(charactersBuilder);
					  pt.postRequest(charactersBuilder.toString());
					  charactersBuilder=new StringBuilder();
				  }
				  Thread thread=new Thread();
				  thread.sleep(500);
			  }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
