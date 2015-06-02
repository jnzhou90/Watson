package com.ibm.findyourlove.db;

import java.util.List;

import com.ibm.findyourlove.model.Person;

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
	 * if can not find this person, return null
	 * @param personId person unique id
	 * @return the person
	 */
	Person getPerson(String personId);
	
	/**
	 * get person set information with its query 
	 * if can not find this person, return null
	 * @param gender value: male or femal
	 * @return
	 */
	List<Person> getPersonList(String gender);



}
