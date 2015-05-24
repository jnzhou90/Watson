package test;

import java.util.List;

import com.ibm.findyourlove.db.CloudantClientImpl;
import com.ibm.findyourlove.model.Person;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 24, 2015 2:49:03 PM 
 * Description:
 * TODO 
 */
public class CloudantClientTest {

	public static void main(String[] args) {
		CloudantClientImpl cc = new CloudantClientImpl();
		
		try {
			
			String query="{\"gender\":\"male\"}";
			
			List<Person> people = cc.getPersonList(query);
			System.out.println("There are " + people.size() + "male.");
			
//			cc.deletePerson(p);
//			cc.deletePerson(p2);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			cc.closeDBConnector();
		}

	}

}
