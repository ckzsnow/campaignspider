package com.ys.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ReadTxtFile {
	
	private static List<String> characterslist = new ArrayList<>();


	public static void main(String[] args) {
		String readFilePath = System.getProperty("user.dir") + File.separator + "writerCharacters.txt";
//		String writerFilePath = System.getProperty("user.dir") + File.separator + "writerCharacters.txt";
		try {
			readFileByCharteArray(readFilePath);
//			writeFile(writerFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void readFileByCharteArray(String filepath) throws IOException {
		
		BufferedReader br=new BufferedReader(new FileReader(filepath)); 
		String str=null;
		StringBuffer buf = new StringBuffer();
		PostTest pt=new PostTest();
		while((str=br.readLine())!=null){
			Thread thread =new Thread();
		buf.append(str);
		buf.append("\r\n");
		pt.postRequest(str);
		try {
			thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
		String buffString=buf.toString();
		char[] buffChars = buffString.toCharArray();
		
//		for(int i=0;i<buffString.length();i++){
////			characterslist.add(String.valueOf(buffChars[i]));
//		}
//		System.out.println(characterslist.size());
//
//		br.close();
		}
	public static void writeFile(String path){
		File file =new File(path);
		//judge file whether exists
		if(file.exists()==false){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 try {
			Writer writer=new FileWriter(file);
			int nums=0;
			for(int i=0;i<(characterslist.size());i++){
				for(int j=0;j<characterslist.size();j++){
					writer.write(characterslist.get(i));
					writer.write(characterslist.get(j));
					writer.write("\r\n");
				}
			}
			System.out.println("写入完成");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
	}
}
