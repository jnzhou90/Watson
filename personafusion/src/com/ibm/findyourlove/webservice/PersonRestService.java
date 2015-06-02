package com.ibm.findyourlove.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;




import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

import com.ibm.findyourlove.controller.IPersonController;
import com.ibm.findyourlove.controller.PersonControllerImpl;

/** 
 * @author Yang Zhong(Jack)
 * @version time：May 25, 2015 4:18:15 PM 
 * Description:  person controller RESFfull API
 */
@Path("/person")
public class PersonRestService {
	
	// http://localhost:8080/liberty-HelloWorld/api/person/name
	// http://findyourlove.mybluemix.net/api/person/name

	//test
	@GET
	@Path("/name")
	public String getName() {
		return "test name";
	}
	

	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/testJson")
	public Response testJson(String query) {
		System.out.println(query);
		//test
		String json="{\"test\":\"test\"}";
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}
	//---------------------

	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getPerson")
	public Response getPerson(String personId) {
		IPersonController personController=new PersonControllerImpl();
		
		String json=personController.getPerson(personId);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}

	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getPersonList")
	public Response getPersonList(String query) {
		IPersonController personController=new PersonControllerImpl();
		String json=personController.getPersonList(query);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}

	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getRankResult")
	public Response getRankResult(String query) {
		IPersonController personController=new PersonControllerImpl();
		String json=personController.getRankResult(query);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}
   
}
