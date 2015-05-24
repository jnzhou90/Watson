package com.ibm.findyourlove.controller;

import javax.ws.rs.Path;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 14, 2015 11:22:45 AM 
 * Description:
 * RESTfull Service API for UI and outside user
 */
public interface IPersonController {
	
	/**
	  * get a person information with its unique id
	  * @param personId person unique id
	  * @return JSON format
	  * Person JSON Demo:
	  {
		"id":"yzbj@cn.ibm.com",
		"name":"Devaid",
		"age":"25",
		"gender":"female",
		“photo”:”/temp/a.jsp”
		"socialData":"xxxx",
		"personalInsights":"TODO"
	   }
	  */
	 @Path("/getPerson")
	 String getPerson(String personId);
	 
	 
	/**
	 * get person set information with its query 
	 * @param query  JSON format
	 * query JSON Demo:
	   {
         "gender":"male"
       }
	 * @return person set which matched query conditions
	 * JSON Demo:
	   {
	        "rankPersons":[
	          	  {
					"id":"xiaopeng@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"90"
				  },
				  {
					"id":"moxuan@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"80"
				  },
				  {
					"id":"qiaoxi@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"70"
				  }
	        ]
	   }
	 */
	 @Path("/getPersonSet")
	String getPersonSet(String query);

	 
	  /**
	   * get the matched person, and return the top N rank list for the given person
	   * if N> matched person num, then return all the matched person,if query=null,return all person
	   * @param personId the unique id for the given person
	   * @param query
	   * @param topN  top number of rank result
	   * @return  JSON format of the matched persons list with ranked list
	   * JSON Demo:
	   {
	        "rankPersons":[
	          	  {
					"id":"xiaopeng@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”
					"socialData":"xxxx"
				  },
				  {
					"id":"moxuan@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”
					"socialData":"xxxx"
				  },
				  {
					"id":"qiaoxi@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"gender":"female",
					“photo”:”/temp/a.jsp”
					"socialData":"xxxx"
				  }
	        ]
	   }
	   */
	 @Path("/getRankResult")
	  String getRankResult(String personId,String query,String topN);

}
