package com.ibm.findyourlove.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ibm.findyourlove.db.CloudantClientImpl;
import com.ibm.findyourlove.db.ICloudantClient;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.JsonUtils;

/** 
 * @author Yang Zhong
 * @version time：May 17, 2015 2:24:04 PM 
 * Description:
 * TODO 
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
	public String getPersonSet(String query) {
		List<Person> person=cloudantClient.getPersonList(query);
		if(person.size()>0){
			return JsonUtils.getListPersonJson(person);
		}else {
			return null;
		}
		
	}

	@Override
	public String getRankResult(String personId, String query, String topN) {		
		//get query person
		Person queryPerson=cloudantClient.getPerson(personId);
		
		//get person list which matched query person
		List<Person> matchPerson=cloudantClient.getPersonList(query);
		
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
