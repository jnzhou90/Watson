package com.ibm.findyourlove.controller;

import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.ibm.findyourlove.db.CloudantClientImpl;
import com.ibm.findyourlove.db.ICloudantClient;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.QueryPara;
import com.ibm.findyourlove.util.JsonUtils;

/** 
 * @author Yang Zhong
 * @version time：May 17, 2015 2:24:04 PM 
 * Description: person controller API
 */
public class PersonControllerImpl implements IPersonController {
	
	static ICloudantClient cloudantClient=new CloudantClientImpl();

	@Override
	public String getPerson(String personId) {
		Person person=cloudantClient.getPerson(personId);
		if(person==null)
		   return null;
		else{	
			return JsonUtils.getJson(person);
		}
	}

	@Override
	public String getPersonList(String query) {
		List<Person> person=cloudantClient.getPersonList(query);
		if(person.size()>0){
			return JsonUtils.getListPersonJson(person);
		}else {
			return null;
		}
		
	}

	@Override
	public String getRankResult(String query) {		
		//parse json query parameters
		Gson gson = new Gson();
		QueryPara queryPara=gson.fromJson(query, QueryPara.class);	
		
		String personId=queryPara.getPersonId();
		String gender=queryPara.getGender();
		
		//get query person
		Person queryPerson=cloudantClient.getPerson(personId);
		
		//get person list which matched query person
		List<Person> matchPerson=cloudantClient.getPersonList(gender);
		
		if(matchPerson.size()==0){
			return null;
		}
		
		//sort matched persons
		rankMatchPersons(queryPerson,matchPerson);
		
		return JsonUtils.getListPersonJson(matchPerson);
	}
	
	/**
	 * sort matched persons
	 * @param queryPerson
	 * @param matchPerson
	 */
	private void rankMatchPersons(Person queryPerson,List<Person> matchPerson){
		for(Person p:matchPerson){
			//query person
			p.setQueryPerson(queryPerson);
		}
		
		Collections.sort(matchPerson);
		this.convertScores(matchPerson);
	}
	
	//TODO not understand this method
	private void convertScores(List<Person> people)
	{
		double minDist = Double.MAX_VALUE;
		for(Person p : people)
		{
			if(minDist > p.getDistToQueryPerson())
				minDist = p.getDistToQueryPerson();
		}
		
		for(Person p : people)
		{
			p.setDistToQueryPerson( (minDist*100.0 / (p.getDistToQueryPerson())) );
		}
	}

}
