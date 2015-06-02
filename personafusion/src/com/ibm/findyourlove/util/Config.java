package com.ibm.findyourlove.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.ibm.json.java.JSONObject;

public class Config 
{
    
    /** Watson Personal Insights **/
	public static final String PersonalInsightsServiceName="personality_insights";
    public static final String PersonalInsightsProfileAPI =  "/v2/profile";
    public static final String WATSON_VIZ_API = "/api/v2/visualize";
	
    /** Cloudant **/
//    public static final int CLOUDANT_PORT = 443;
    public static final String CLOUDANT_NAME = "cloudantNoSQLDB";

    /* Mobile Data Config */
    public static final String MOBILE_DATA_APP_ID = "";
    public static final String MOBILE_DATA_APP_SECRET = "";
    
	/**
	 * Gets the Bluemix <b>VCAP_SERVICES</b> environment variable and return it as a JSONObject.
	 * @return the VCAP_SERVICES as Json
	 */
	public static JSONObject getVCAPServices() {
		String envServices = System.getenv("VCAP_SERVICES");
		if (envServices == null)
			return null;
		JSONObject sysEnv = null;
		try {
			sysEnv = JSONObject.parse(envServices);
		} catch (IOException e) {
			System.err.println("Error parsing VCAP_SERVICES: ");
			e.printStackTrace();
		}
		return sysEnv;
	}
	
	/**
	 * get classes path of the project
	 * demo: D:\java\apache-tomcat-7.0.28\webapps\RegistServiceSystem\WEB-INF\classes\
	 * @return
	 */
	public static String getClassesPath() {
		
		String classPath = Config.class.getResource("/").getPath().substring(1);
		try {
			classPath = java.net.URLDecoder.decode(classPath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// windows path
		if ("\\".equals(File.separator)) 
			classPath = classPath.replace("/", "\\");
		// linux path
		if ("/".equals(File.separator)) 
			classPath = classPath.replace("\\", "/");
		
		return classPath;
	}
	
}
