package com.ibm.findyourlove.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import com.ibm.findyourlove.db.CloudantClientImpl;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.Config;
import com.ibm.findyourlove.util.Constants;
import com.ibm.findyourlove.util.FileTool;
import com.ibm.findyourlove.util.JsonUtils;

/**
 * 
 * @author Yang Zhong
 * 
 */
public class PersonListGenerator {

	
	public static void main(String[] args) {
		List<Person> personList = generateDistinctPeople(100);	
		
		String personJson=JsonUtils.getListPersonJson(personList);
		System.out.println(personJson);
		
	    //put generate people into db
		CloudantClientImpl cc = new CloudantClientImpl();
		cc.deleteAll();
		
		for(Person person: personList){
			cc.putPerson(person);
		}
		
//		File dest=new File(FileTool.insightsFolder+"out.json");
//		try {
//			FileTool.writeFileContent(dest, personJson);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	/**
	 * Generate a new randomized list of N people with unique names and images and social data for personal insights
	 * @param numPeople
	 * @return
	 */
	public static List<Person> generateDistinctPeople (int numPeople) {
				
		List<Person> result = new ArrayList<Person>();

		int pCounter = 1;
		
		String imgURL = "";
		String gender=null;
		List<Trait> traitList;
		
		String imageFileFullPath=FileTool.imageFileFullPath;
		String nameFileFullPath=FileTool.nameFileFullPath;
		
		String maleInsightsFolder=FileTool.maleInsightsFolder;
		String femalInsightsFolder=FileTool.femalInsightsFolder;
		
		try {
			//image data source
//			URL url = new URL("https://dl.dropboxusercontent.com/u/27101002/personafusion/imageLinks.txt");
			BufferedReader reader = new BufferedReader( new FileReader(imageFileFullPath));
			Scanner scan0 = new Scanner(reader);
			
			// Set<String> nameList = NameGenerator.generateDistinctFullNames(numPeople);
			Set<String> nameList = getNameList();
			for(String name : nameList) {	
				
                String insightsFileFullPath=null;
				//first 50 is male, the other 50 is femal in image data source
				if(pCounter<=50){
					gender=Constants.MALE;
					insightsFileFullPath=maleInsightsFolder+pCounter+".txt";
				}else{
					gender=Constants.FEMAL;
					insightsFileFullPath=femalInsightsFolder+(--pCounter)+".txt";
				}
				
				//get traits from watson
				String socialData=FileTool.getFileContent(new File(insightsFileFullPath));
				WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
				List<Trait> traits = watsonPersonalInsights.getTraitsList(socialData);		
				
				//test
//				List<Trait> traits =new ArrayList<Trait>();
				
				if(scan0.hasNextLine()) {
					imgURL = scan0.nextLine();
				}				
				String image_url=imgURL;
				
				String id=name; //here just use name as id 
				int age=getAge(20, 30);
				

				
				Person newPerson=new Person(id, name, age, gender, image_url, socialData, traits);
				
				result.add(newPerson);
				
				if(pCounter>100)
					break;
				
				pCounter++;				
			}
			scan0.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
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
	
	/**
	 * random generate aget between [min,max]
	 * @param min
	 * @param max
	 * @return
	 */
	private static int getAge(int min,int max){
        Random random = new Random();
        int age = random.nextInt(max)%(max-min+1) + min;
        return age;
	}
}
