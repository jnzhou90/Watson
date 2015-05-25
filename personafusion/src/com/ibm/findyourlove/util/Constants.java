package com.ibm.findyourlove.util;

import java.io.File;

public class Constants 
{
	
	public static final String ID_KEY = "_id";//unique id
	public static final String Gender="gender"; 
	public static final String JSON_KEY = "json"; //this property store person object in db
	
	//gender value
	public static final String MALE="male";
	public static final String FEMAL="femal";
	
	//data source
	static String FS=File.separator;
	public static final String IMAGEPATH=Config.getClassesPath()+"personData"+FS+"images"+FS+"imageLinks.txt";
	public static final String NAMEPATH=Config.getClassesPath()+"personData"+FS+"name"+FS+"output.txt";
	public static final String INSIGHTSINPUTFOLDER=Config.getClassesPath()+"personData"+FS+"personalInsightsInput"+FS;
}
