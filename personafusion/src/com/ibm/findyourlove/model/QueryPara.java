package com.ibm.findyourlove.model;

/**
 * @author Yang Zhong
 * @version time：May 24, 2015 3:28:29 PM Description: Query model for db
 */
public class QueryPara {
	private String personId;
	private String gender;

	public QueryPara(String personId, String gender) {
		super();
		this.personId = personId;
		this.gender = gender;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	

}
