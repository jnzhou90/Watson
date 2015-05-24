package com.ibm.findyourlove.util;

import java.io.IOException;


import com.ibm.json.java.JSONObject;

public class Config 
{
    
    /** Watson Personal Insights **/
	public static final String PersonalInsightsServiceName="personality_insights";
    public static final String PersonalInsightsProfileAPI = "/api/v2/profile";
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
}
