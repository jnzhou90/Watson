package com.ibm.findyourlove.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.findyourlove.controller.IPersonController;
import com.ibm.findyourlove.controller.PersonControllerImpl;

/** 
 * @author Yang Zhong(Jack)
 * @version time：May 25, 2015 4:18:15 PM 
 * Description:  person controller RESFfull API
 */
@Path("/person")
public class PersonRestService {
	
	private IPersonController personController=new PersonControllerImpl();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Path("/getPerson")
	public Response getPerson(String personId) {
		String json=personController.getPerson(personId);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getPersonList")
	public Response getPersonList(String query) {
		String json=personController.getPersonList(query);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getRankResult")
	public Response getRankResult(String query) {
		String json=personController.getRankResult(query);
		
		return Response.ok(json).header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
	            .build();
	}
   
}
