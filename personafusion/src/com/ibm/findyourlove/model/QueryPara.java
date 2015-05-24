package com.ibm.findyourlove.model;

/**
 * @author Yang Zhong
 * @version time：May 24, 2015 3:28:29 PM Description: Query model for db
 */
public class QueryPara {
	
	private String gender;

	public QueryPara(String gender) {
		super();
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}
