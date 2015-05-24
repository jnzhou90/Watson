package com.ibm.findyourlove.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.Constants;

/**
 * 
 * @author Yang Zhong
 * 
 */
public class PersonListGenerator {

	
	public static void main(String[] args) {
		generateDistinctPeople(100);		
	}
	
	/**
	 * Generate a new randomized list of N people with unique names and images and social data for personal insights
	 * @param numPeople
	 * @return
	 */
	public static List<Person> generateDistinctPeople (int numPeople) {
				
		List<Person> result = new ArrayList<Person>();

		int pplCounter = 1;
		
		String imgURL = "";
		List<Trait> traitList;
		
		
		try {
			//image data source
			URL url = new URL("https://dl.dropboxusercontent.com/u/27101002/personafusion/imageLinks.txt");
			Scanner scan0 = new Scanner(url.openStream());
			
			// Set<String> nameList = NameGenerator.generateDistinctFullNames(numPeople);
			Set<String> nameList = getNameList();
			for(String name : nameList) {
				// Call Watson to get traits. Call keyword converter to get keywords.
				
				if(scan0.hasNextLine()) {
					imgURL = scan0.nextLine();
				}				

				System.out.println("PplCounter: " + pplCounter);
				
				//TODO get socail data from local json
				String socialData="TODO";
				
				//get traits from watson
				WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
				List<Trait> traits = watsonPersonalInsights.getTraitsList(socialData);		
				
				String id=name; //here just use name as id 
				//TODO first 50 is male, the other 50 is femal in image data source
				String gender=Constants.FEMAL;
				String image_url=imgURL;
				//TODO use random method to generate age is [20,30]
				int age=24;
				
				Person newPerson=new Person(id, name, age, gender, image_url, socialData, traits);
				
				result.add(newPerson);
				
				pplCounter++;				
			}
			scan0.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		return result;
	}
	
	public static Set<String> getNameList() {
		Set<String> result = new HashSet<String>();
		
		try {
			URL url = new URL("https://dl.dropboxusercontent.com/u/27101002/personafusion/output.txt");
			Scanner scan = new Scanner(url.openStream());
			while(scan.hasNextLine()) {
				String line = scan.nextLine();
				result.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
}
