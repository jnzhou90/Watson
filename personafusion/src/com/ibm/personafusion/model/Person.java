package com.ibm.personafusion.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.ibm.personafusion.controller.JsonUtils;

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
    public String sex;//value is male or female
	public String image_url; // person photo url
	public String socialData; //used as watson personal insights input data
    public List<Trait> traits; //personalInsights
	//different distances for everyone
	public double distToQueryPerson; //TODO change this method to private after code
	
	//one set of weights for everyone
	static double weightTraits;
    
    //--------------------------------------------------------------------------------
	public enum Role {DEV, Manager};
	public Role role;
	public ResumeInfo resumeInfo;
	//This will be the list of answers from the q & a of the person
	//index 0 response to question 1, index 1 response to question 2 etc...
	public List<String> qaResponses;

	public List<String> keyWords;
	
	public String group;
	
	//TODO need to delete this constructor after code
	static double weightResume=1, weightRole=1; 
	
	// --------------------------------------------------------------------------------------
	// --Constructor
	// --------------------------------------------------------------------------------------
	
	public Person(String name, List<Trait> traits, String image_url,List<String> keyWords)
	{
		this.name = name;
		this.traits = traits;
		this.resumeInfo = resumeInfo;
		this.role = role;
		//Set default weights
		this.weightTraits = 1;
		this.weightResume = 1;
		this.weightRole = 1;
		this.image_url = image_url;
		this.qaResponses = new ArrayList<String>();
		this.keyWords = keyWords;
	}
	
	public Person(String name, List<Trait> traits, ResumeInfo resumeInfo)
	{
		this.name = name;
		this.traits = traits;
		this.resumeInfo = resumeInfo;
		this.role = role;
		//Set default weights
		this.weightTraits = 1;
		this.weightResume = 1;
		this.weightRole = 1;
		this.image_url = "";
		this.role = Role.DEV;
		this.qaResponses = new ArrayList<String>();
		this.keyWords = new ArrayList<String>();
	}
	
	public Person(String name, List<Trait> traits, String image_url, ResumeInfo resumeInfo, Role role, List<String> keyWords)
	{
		this.name = name;
		this.traits = traits;
		this.resumeInfo = resumeInfo;
		this.role = role;
		//Set default weights
		this.weightTraits = 1;
		this.weightResume = 1;
		this.weightRole = 1;
		this.image_url = image_url;
		this.qaResponses = new ArrayList<String>();
		this.keyWords = keyWords;
	}
	
	// --------------------------------------------------------------------------------------
	// --API
	// --------------------------------------------------------------------------------------
	
	/**
	 * TODO return JSON format of the person
	 * @return JSON format
	 */
	public String toJSONFormat(){
		/*
		{
			"id":"yzbj@cn.ibm.com",
			"name":"Devaid",
			"age":"25",
			"sex":"female",
			“photo”:”/temp/a.jsp”
			"socialData":"xxxx"
	   }
		 */
		
		JsonUtils jsonUtils = new JsonUtils();
		//TODO need to change jsonUtils.getJson method to fit our json format
		 jsonUtils.getJson(this);
		
		return null;
	}//end method
	
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
	
	public List<FollowUp> getFollowUp() {
		return Collections.singletonList(new FollowUp(name, image_url, role));
	}
	
	public void setDistanceWeights(double weightTraits, double weightResume, double weightRole)
	{
		this.weightTraits = weightTraits;
		this.weightResume = weightResume;
		this.weightRole = weightRole;
	}
	
	/*
	 * Remove stop words and return N most frequent words
	 */
	public List<String> getKeyWords(int nMostFrequent)
	{
		List<String> stopWords = new ArrayList<String>();
		try
		{
			URL url = new URL("https://dl.dropboxusercontent.com/u/27101002/personafusion/stopWords.txt");
			Scanner sc = new Scanner(url.openStream());
			while(sc.hasNextLine())
			{
				String word = sc.nextLine();
				word = word.trim();
				stopWords.add(word);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		List<String> keyWords = new ArrayList<String>();
		Map<String, Integer> keyMapCount = new TreeMap<String, Integer>();
		for(String tweet : this.qaResponses)
		{
			String[] tweetParts = tweet.split(" ");
			for(String tweetWord : tweetParts)
			{
				if(tweetWord.length() < 2)
					continue;
				tweetWord = tweetWord.toLowerCase();
				if(keyMapCount.containsKey(tweetWord))
				{
					int value = keyMapCount.get(tweetWord);
					keyMapCount.put(tweetWord, value+1);
				}
				else
				{
					keyMapCount.put(tweetWord, 1);
				}
			}
		}
		
		List<TweetTerm> termList = new ArrayList<TweetTerm>();
		List<String> stopWordsLower = new ArrayList<String>();
		for(String stopWord : stopWords)
			stopWordsLower.add(stopWord.toLowerCase());
		for(String  tweetWord : keyMapCount.keySet())
		{
			//tweetWord = tweetWord.toLowerCase();
			//System.out.println(tweetWord);
			//System.out.println(keyMapCount.get(tweetWord));
			String tempTweet = tweetWord.toLowerCase();
			boolean cleanTweet = true;
			String alph = "abcdefghijklmnopqrstuvwxyz";
			for(char c : tempTweet.toCharArray())
				if(!alph.contains(c + ""))
			{
				cleanTweet = false;
			}
			//System.out.println(tempTweet);
			if(!cleanTweet) continue;
			if(!stopWordsLower.contains(tempTweet))
				termList.add(new TweetTerm(tweetWord, keyMapCount.get(tweetWord)));
		}
		Collections.sort(termList);
		for(int i=0; i<nMostFrequent; i++)
		{
			if (i == termList.size()) break;
			keyWords.add(termList.get(i).word);
		}
		return keyWords;
	}
	
	class TweetTerm implements Comparable<TweetTerm>
	{
		String word;
		int wordCount;
		
		TweetTerm(String word, int wordCount)
		{
			this.word = word;
			this.wordCount = wordCount;
		}
		
		public int compareTo(TweetTerm other) 
		{
			return other.wordCount - this.wordCount;
		}
		
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
