package com.ibm.findyourlove.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;

import sun.nio.cs.ext.TIS_620;

import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.QueryPara;
import com.ibm.findyourlove.model.Trait;
import com.ibm.findyourlove.service.WatsonPersonalInsights;
import com.ibm.findyourlove.util.Config;
import com.ibm.findyourlove.util.Constants;
import com.ibm.findyourlove.util.JsonUtils;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

/** A class for communicating with the Cloudant datastore. 
 *  See main() for example usage.
 *  
 *  @author Yang Zhong**/
public class CloudantClientImpl implements ICloudantClient
{
	private HttpClient httpClient;
	private CouchDbConnector dbc;
	
	private String name; //db name
	private String username;
	private String password;
	private String host;	
	private int port;

	private JSONArray cloudant;
	private JSONObject cloudantInstance;
	private JSONObject cloudantCredentials;
	
	private static Logger logger = Logger.getLogger(CloudantClientImpl.class.getName());
	
	// --------------------------------------------------------------------------------------
	// --Constructor
	// --------------------------------------------------------------------------------------
	public CloudantClientImpl()
	{
		//read env VCAP_SERVICES and parse it into JSON
		logger.info("Processing VCAP_SERVICES for Personal Insights");
//		JSONObject vcap = Config.getVCAPServices();

//		this.cloudant=(JSONArray)vcap.get(Config.CLOUDANT_NAME);
//		this.cloudantInstance=(JSONObject)this.cloudant.get(0);
//		this.cloudantCredentials=(JSONObject)cloudantInstance.get("credentials");
//		
//		this.name = Config.CLOUDANT_NAME;
//		this.username = (String)cloudantCredentials.get("username");
//		this.password = (String)cloudantCredentials.get("password");
//		this.host = (String)cloudantCredentials.get("host");
//		this.port = (int)cloudantCredentials.get("port");
		
		
		//this is for test
		this.name = Config.CLOUDANT_NAME;
		this.username = "e5ed1971-86ef-4a5b-94c1-9e39d6f6eb98-bluemix";
		this.password = "5b0f760e2e436c98cff0b4844ded286f0357b09a7ba8ab1cbf822b3c43f4a7db";
		this.host = "e5ed1971-86ef-4a5b-94c1-9e39d6f6eb98-bluemix.cloudant.com";
		this.port = 443;
		//String url="https://e5ed1971-86ef-4a5b-94c1-9e39d6f6eb98-bluemix:5b0f760e2e436c98cff0b4844ded286f0357b09a7ba8ab1cbf822b3c43f4a7db@e5ed1971-86ef-4a5b-94c1-9e39d6f6eb98-bluemix.cloudant.com"
		
		//-------------------------------------
		this.httpClient = null;

		this.dbc = this.createDBConnector();
	}
	
	// --------------------------------------------------------------------------------------
	// --API
	// --------------------------------------------------------------------------------------
	
	@Override
	public Person getPerson(String personId){
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = this.dbc.get(HashMap.class, personId);
		Person p = JsonUtils.getPersonFromJson((String)obj.get(Constants.JSON_KEY));
		return p;

	}
	
	@Override
	public List<Person> getPersonList(String query) {

		QueryPara queryPara=JsonUtils.getQueryPara(query);
		String gender=queryPara.getGender();
		
		List<Person> people = new ArrayList<Person>();
		List<String> docIds = dbc.getAllDocIds();
		for(String docId : docIds)
		{
			@SuppressWarnings("unchecked")
			HashMap<String, Object> obj = this.dbc.get(HashMap.class, docId);
			if (obj.get(Constants.Gender) != null &&
				obj.get(Constants.Gender).equals(gender))
			{
				String json = (String)obj.get(Constants.JSON_KEY);
				Person p = JsonUtils.getPersonFromJson(json);
				people.add(p);
			}
		}
		System.out.println(String.format(
					"Retrieved %d Person entries for group %s.", 
					people.size(), gender));
		return people;
	}
	
	/** Delete all documents from the Cloudant datastore. Use with caution. **/
	public void deleteAll()
	{
		List<String> docIds = this.dbc.getAllDocIds();
		int startSize = docIds.size();
		for(String docId : docIds)
		{
			@SuppressWarnings("unchecked")
			HashMap<String, Object> obj = this.dbc.get(HashMap.class, docId);
			this.dbc.delete(obj);
		}
		docIds = this.dbc.getAllDocIds();
		int endSize = docIds.size();
		System.out.println(
				String.format(
					"Deleted all entries. Starting size: %d. Current size: %d.",
					startSize, endSize));
	}
	
	public void deletePerson(Person p)
	{
		String name = p.name.toUpperCase();
		List<String> docIds = this.dbc.getAllDocIds();
		int startSize = docIds.size();
		@SuppressWarnings("unchecked")
		HashMap<String, Object> obj = this.dbc.get(HashMap.class, name);
		this.dbc.delete(obj);
		docIds = this.dbc.getAllDocIds();
		int endSize = docIds.size();
		System.out.println(
				String.format(
					"Deleted entry %s. Starting size: %d. Current size: %d.",
					name, startSize, endSize));
	}
	
	/** Put a Person into Cloudant using person.name as the unique id.
	 */
	public void putPerson(Person p)
	{
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put(Constants.ID_KEY, p.id);
		data.put(Constants.Gender,p.gender);
		
		data.put(Constants.JSON_KEY, JsonUtils.getJson(p));
		this.putItem(data);
	}
	
	public void closeDBConnector()
	{
		if (httpClient != null)
		{
			httpClient.shutdown();
		}
	}
	
	// --------------------------------------------------------------------------------------
	// --private method
	// --------------------------------------------------------------------------------------
	private CouchDbConnector createDBConnector() 
	{
		CouchDbInstance dbInstance = null;
		
		System.out.println("Creating CouchDB instance...");
		System.out.println(this.username);
		this.httpClient = new StdHttpClient.Builder()
		.host(this.host)
		.port(this.port)
		.username(this.username)
		.password(this.password)
		.enableSSL(true)
		.relaxedSSLSettings(true)
		.build();
		
		dbInstance = new StdCouchDbInstance(this.httpClient);
		CouchDbConnector dbc = new StdCouchDbConnector(this.name, dbInstance);
		dbc.createDatabaseIfNotExists();
		return dbc;
	}
	
	/** Put a generic item modeled as Key-Value pairs into Cloudant. **/
	private void putItem(HashMap<String, Object> data)
	{
		if (data == null) 
		{ 
			System.err.println("data cannot be null in putItem()"); 
			return;
		}
		String id = (String)data.get(Constants.ID_KEY);
		if (id == null)   
		{ 
			System.err.println("data must have an _id field."); 
			return;
		}
		if (this.dbc.contains(id)) 
		{ 
			System.err.println("Didn't putItem. _id=" + id + " already exists."); 
			return;
		}
		this.dbc.create(data);
		System.out.println("Put _id=" + id + " into the datastore."); 
	}


}
