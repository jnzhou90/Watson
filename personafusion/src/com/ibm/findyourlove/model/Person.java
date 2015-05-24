package com.ibm.findyourlove.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.ibm.findyourlove.util.JsonUtils;

public class Person implements Comparable<Person>
{
	
	// --------------------------------------------------------------------------------------
	// --properties
	// --------------------------------------------------------------------------------------
		
    //one queryPerson for everyone
	static Person queryPerson;
	
	public String id;
    public String name;
    public int age;
    public String gender;//value is male or female
	public String image_url; // person photo url
	public String socialData; //used as watson personal insights input data
    public List<Trait> traits; //personalInsights
	//different distances for everyone
	public double distToQueryPerson; //TODO change this method to private after code
	
	//one set of weights for everyone
	static double weightTraits;
	
	// --------------------------------------------------------------------------------------
	// --Constructor
	// --------------------------------------------------------------------------------------
	
	public Person(String id, String name, int age, String sex,
			String image_url, String socialData, List<Trait> traits) {
		super();
		this.id = id;
		this.name = name.toUpperCase();
		this.age = age;
		this.gender = sex;
		this.image_url = image_url;
		this.socialData = socialData;
		this.traits = traits;
	}
	
	// --------------------------------------------------------------------------------------
	// --API
	// --------------------------------------------------------------------------------------
	
	public void setQueryPerson(Person p)
	{
		this.queryPerson = p;
	}

	
	public double getDistToQueryPerson(){
		return this.distToQueryPerson;
	}
	
	public void setDistToQueryPerson(Double distToQueryPerson){
		this.distToQueryPerson=this.distToQueryPerson;
	}

	public int compareTo(Person other) 
	{
		//sort bases off min distance from query perosn
		//weight different distances between resume skills, traits, and role
		double thisDistance = this.getDistanceToQueryPerson();
		double otherDistance = other.getDistanceToQueryPerson();
		
		//TODO test
		System.out.println("distance-------------------");
		System.out.println("this class "+this.getClass().getName());
		System.out.println("other class "+other.getClass().getName());
		System.out.println("thisDistance="+thisDistance);
		System.out.println("otherDistance="+otherDistance);
		
		if(thisDistance < otherDistance)
			return -1;
		if(thisDistance > otherDistance)
			return 1;
		else
			return 0;
	}
	//---------------------------------------------------------
	
	public String toString()
	{
		String pString = "";
		pString = traits.toString();
		//pString = this.name + " , " + this.distToQueryPerson;
		return pString;
	}
	
	// --------------------------------------------------------------------------------------
	// --private method
	// --------------------------------------------------------------------------------------
	
	/**
	 * using editor algorithm to get this person distance to the query person
	 * distance=(x1-y1)^2+(x2-y2)^2+...+(xN-yN)^2,n is the according trait, xN is the according trait value
	 * @return distance of this person to the query person
	 */
	private double getDistanceToQueryPerson()
	{
		
		double distance = 0, distanceTraits = 0, distanceResume = 0, distanceRole = 0;
		
		//get query person distanceTraits
		for(int i=0; i<this.queryPerson.traits.size(); i++)
		{
			String queryTraitName = this.queryPerson.traits.get(i).traitName;
			double queryTraitPercent = this.queryPerson.traits.get(i).percent;
			distanceTraits += this.getTraitDistance(queryTraitName, queryTraitPercent);
		}
		

//		//calculate this person skills distance
//		double distanceTechSkills = 0;
//		for(int i=0; i<this.queryPerson.resumeInfo.techSkills.size(); i++)
//		{
//			//if all query tech skills exist in this person distance is 0
//			//distance grows as skills dont match
//			String techSkill = this.queryPerson.resumeInfo.techSkills.get(i);
//			if(!this.resumeInfo.techSkills.contains(techSkill))
//				distanceTechSkills++;
//			
//		}	
//		
//		
//		double distancePastEmployers = 0;
//		for(int i=0; i<this.queryPerson.resumeInfo.pastEmployers.size(); i++)
//		{
//			String pastEmployer = this.queryPerson.resumeInfo.pastEmployers.get(i);
//			if(!this.resumeInfo.techSkills.contains(pastEmployer))
//				distancePastEmployers++;
//		}
//		distanceResume = (.75 * distancePastEmployers) + (.25 * distanceTechSkills);
//		
//		if(!this.queryPerson.role.equals(this.role))
//			distanceRole = 1;
		
//		distance = (weightTraits * distanceTraits) + (weightResume * distanceResume) + (weightRole * distanceRole);
		
		//TODO now just cosider the personal insights traits as influence factor for couple match. Need to add other factors like height,education,appearance and so on for couple match.
		distance = (weightTraits * distanceTraits);
				
		this.distToQueryPerson = distance;
		return distance;
	}
	
	private double getTraitDistance(String queryTrait, double queryScore)
	{
		double distance = 1;//worst case if the trait does not exist return max value 1
		for(int i=0; i<this.traits.size(); i++)
		{
			if(queryTrait.equals(this.traits.get(i).traitName))
			{
				double tempDist = (queryScore/100.0) - (this.traits.get(i).percent/100.0);
				distance = Math.pow(tempDist, 2);
				break;//found trait distance break loop
			}
		}
		return distance;
	}
	
	
}
