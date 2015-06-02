package com.ibm.findyourlove.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * File operation tool
 * @author Yang Zhong
 * @date 2015-5-25 14:40
 */

public class FileTool {
	
	//data source
	static String FS=File.separator;
	public static String imageFileFullPath=Config.getClassesPath()+"personData"+FS+"images"+FS+"imageLinks.txt";
	public static String nameFileFullPath=Config.getClassesPath()+"personData"+FS+"name"+FS+"output.txt";
	public static String insightsFolder=Config.getClassesPath()+"personData"+FS+"personalInsightsInput"+FS;
	public static String maleInsightsFolder=Config.getClassesPath()+"personData"+FS+"personalInsightsInput"+FS+"M"+FS;
	public static String femalInsightsFolder=Config.getClassesPath()+"personData"+FS+"personalInsightsInput"+FS+"F"+FS;
	
	//---------------------------------------------------------Public Methods
	

	/**
	 * 将文件内容从文件src拷贝到文件dest
	 * @param  src  需要拷贝的文件
	 * @param  dest 拷贝目标地址
	 */
	public static void copyFile(File src, File dest) throws Exception{

		  int length=2097152;
		  if(!src.exists()){
			  throw new IOException("需要拷贝的源文件不存在：" + src.getName());
		  }
		  
		  FileInputStream in=new FileInputStream(src);
		  FileOutputStream out=new FileOutputStream(dest);
		  byte[] buffer=new byte[length];
		  int i=0;
		  while((i=in.read(buffer))!= -1){
		      out.write(buffer,0,i);
		  }
		  in.close();
		  out.close();
	}
	
	/**
	 * 读取目标文件内容
	 * @param src 需要读取的文件
	 * @return 字符串形式的文件内容
	 */
	public static String getFileContent(File src) throws Exception{
		
		  String content = "", lineStr;
		  BufferedReader reader = new BufferedReader( new FileReader(src));
          
	      while((lineStr = reader.readLine())!=null){
			     content = content + "\n" + lineStr;
		  }
          reader.close();
          
		  return content;
	}
	
	/**
	 * 将字符串写入到目标文件中
	 * @param dest 目标文件
	 * @param content 写入字符串内容
	 */
	public static void writeFileContent(File dest, String content) throws Exception{
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
		writer.write(content);
		writer.close();
		
	}

}
