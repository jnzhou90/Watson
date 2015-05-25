package test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.QueryPara;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.util.Constants;
import com.ibm.findyourlove.util.JsonUtils;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 24, 2015 1:53:24 PM 
 * Description:
 * TODO 
 */
public class JsonUtilsTest {

	public static void main(String[] args) {
		
//		String id="yzyangbj@cn.ibm.com";
//		String name="Test1";
//		String sex="male";
//		String image_url="/images/test.jpg";
//		String socialData="socialData";
//		int age=24;
//		List<Trait> traits = new ArrayList<Trait>();
//		traits.add(new Trait("programming", .9));
//		traits.add(new Trait("being awesome", .95));
//		Person person=new Person(id, name, age, sex, image_url, socialData, traits);
//		
//		System.out.println(JsonUtils.getJson(person));
		
//		Person p = JsonUtils.getPersonFromJson((String)obj.get(Constants.JSON_KEY));
		
		Gson gson = new Gson();
		String query="{\"gender\":\"male\"}";
		QueryPara queryCondition=gson.fromJson(query, QueryPara.class);
		System.out.println(queryCondition.getGender());
		
		System.out.println("---------------------");
		String query2="{\"personId\":\"yz\",\"gender\":\"male\"}";
		QueryPara queryCondition2=gson.fromJson(query2, QueryPara.class);
		System.out.println(queryCondition2.getPersonId());
		System.out.println(queryCondition2.getGender());
		
	}

}
