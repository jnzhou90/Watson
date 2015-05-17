package com.ibm.personafusion.controller.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.ibm.personafusion.controller.CloudantClientImpl;
import com.ibm.personafusion.controller.IPersonController;
import com.ibm.personafusion.controller.JsonUtils;
import com.ibm.personafusion.db.ICloudantClient;
import com.ibm.personafusion.model.Person;
import com.ibm.personafusion.model.Trait;
import com.ibm.personafusion.service.WatsonPersonalInsights;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 17, 2015 2:24:04 PM 
 * Description:
 * TODO 
 */
public class PersonControllerImpl implements IPersonController {
	
	//TODO just use test, need to change to real db
	static ICloudantClient cloudantClient=new CloudantClientImpl();

	@Override
	public String getPerson(String personId) {
		Person person=cloudantClient.getPerson(personId);
		if(person==null)
		   return null;
		else{
			//set watson personal insights to this person
			setPersonTraits(person);
			
			return person.toJSONFormat();
		}
	}

	@Override
	public String getPersonSet(String query) {
		List<Person> personSet=cloudantClient.getPersonSet(query);
		if(personSet.size()>0){
			return toJSONFormat(personSet);
		}else {
			return null;
		}
		
	}

	@Override
	public String getRankResult(String personId, String query, String topN) {
		// TODO Auto-generated method stub
		
		//get query person
		Person queryPerson=cloudantClient.getPerson(personId);
		
		//get person list which matched query person
		List<Person> matchPersonSet=cloudantClient.getPersonSet(query);
		
		if(matchPersonSet.size()==0){
			return null;
		}
		
		//sort matched persons and set these persons personal insights value
		rankMatchPersons(queryPerson,matchPersonSet);
		
		return toJSONFormat(matchPersonSet);
	}
	
	/**
	 * set watson personal insights to this person
	 * @param person
	 */
	private void setPersonTraits(Person person){
		WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
		List<Trait> traitList = watsonPersonalInsights.getTraitsList(person.socialData);
		person.traits=traitList;
	}
	
	/**
	 * TODO format person set into JSON string
	 * @param personSet
	 * @return
	 */
	private String toJSONFormat(List<Person> personSet){
		 /* JSON Demo:
	   {
	        "rankPersons":[
	          	  {
					"id":"xiaopeng@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"sex":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"90"
				  },
				  {
					"id":"moxuan@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"sex":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"80"
				  },
				  {
					"id":"qiaoxi@cn.ibm.com",
					"name":"Devaid",
					"age":"25",
					"sex":"female",
					“photo”:”/temp/a.jsp”,
					"socialData":"xxxx",
					"personalInsights":"TODO",
					"distToQueryPerson":"70"
				  }
	        ]
	   }
		 */
		return null;
	}
	
	private void rankMatchPersons(Person queryPerson,List<Person> matchPersonSet){
		for(Person p:matchPersonSet){
			//sort matched persons and set these persons personal insights value
			rankMatchPersons(queryPerson,matchPersonSet);
			
			//query person
			p.setQueryPerson(queryPerson);
		}
		
		Collections.sort(matchPersonSet);
		this.convertScores(matchPersonSet);
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
