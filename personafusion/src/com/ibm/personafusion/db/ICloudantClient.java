package com.ibm.personafusion.db;

import java.util.Set;

import com.ibm.personafusion.model.Person;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 15, 2015 11:22:45 AM 
 * Description:
 * Cloudant DB Client API
 */
public interface ICloudantClient {
	
	//if no this person, return null
	
	/**
	 * get a person information with its unique id
	 * @param personId person unique id
	 * @return the person
	 */
	Person getPerson(String personId);

	/**
	 * get person set information with its query 
	 * @param query JSON format
	 *query JSON Demo:
	   {
         "sex":"male"
       }
	 * @return
	 */
	Set<Person> getPersonSet(String query);



}
