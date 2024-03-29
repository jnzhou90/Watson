package com.ibm.findyourlove.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ibm.findyourlove.db.CloudantClientImpl;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.Constants;
import com.ibm.findyourlove.util.JsonUtils;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 24, 2015 2:49:27 PM 
 * Description:
 * TODO 
 */
public class AddPersonInDB {
	
	public static void main(String[] args) {
		CloudantClientImpl cc = new CloudantClientImpl();
		
		try {
			cc.deleteAll();
			
			//add femal person
			for(int i=0;i<3;i++){
				String id=i+"@cn.ibm.com";
				String name="Test"+i;
				String gender=Constants.FEMAL;
				String image_url="/images/test.jpg";
				String socialData="socialData";
				int age=24;
				
				List<Trait> traits = new ArrayList<Trait>();
				traits.add(new Trait("programming", i));
				traits.add(new Trait("being awesome", i));
				
				//get traits from watson
//				WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
//				List<Trait> traits = watsonPersonalInsights.getTraitsList(socialData);
				
				Person person=new Person(id, name, age, gender, image_url, socialData, traits);
				
				cc.putPerson(person);
			}

			//add male person
			for(int i=3;i<6;i++){
				String id=i+"@cn.ibm.com";
				String name="Test"+i;
				String gender=Constants.MALE;
				String image_url="/images/test.jpg";
				String socialData="socialData";
				int age=24;
				
				List<Trait> traits = new ArrayList<Trait>();
				traits.add(new Trait("programming", i));
				traits.add(new Trait("being awesome",i));
				
				//get traits from watson
//				WatsonPersonalInsights watsonPersonalInsights = new WatsonPersonalInsights();
//				List<Trait> traits = watsonPersonalInsights.getTraitsList(socialData);
				
				Person person=new Person(id, name, age, gender, image_url, socialData, traits);
				
				cc.putPerson(person);
			}
			
			//get person
			String query="{\"gender\":\"male\"}";
			
			List<Person> people = cc.getPersonList(query);
			System.out.println("There are " + people.size() + " male.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cc.closeDBConnector();
		}


	}

}
