package test.com.ibm.findyourlove.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ibm.findyourlove.controller.IPersonController;
import com.ibm.findyourlove.controller.PersonControllerImpl;
import com.ibm.findyourlove.model.QueryPara;

/** 
 * @author Yang Zhong
 * @version time：May 24, 2015 8:16:50 PM 
 * Description:
 * TODO 
 */
public class PersonControllerImplTest {
	
	IPersonController personController=new PersonControllerImpl();
	
	String personId="0@cn.ibm.com"; //femal
	String query="{\"gender\":\"male\"}";

	@Test
	public void testGetPerson() {
		String personJson=personController.getPerson(personId);
		
		System.out.println("----------------------------------");
		System.out.println("--getPerson");
		System.out.println(personJson);
	}

	@Test
	public void testGetPeople() {
		String peopleJson=personController.getPersonList(query);
		
		System.out.println("----------------------------------");
		System.out.println("--peopel");
		System.out.println(peopleJson);
	}

	@Test
	public void testGetRankResult() {
		
		String query2="{\"personId\":\"0@cn.ibm.com\",\"gender\":\"male\"}";

		String rankResult=personController.getRankResult(query2);
		
		
		System.out.println("----------------------------------");
		System.out.println("--rankResult");
		System.out.println(rankResult);
	}
	
	

}
