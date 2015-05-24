package com.ibm.findyourlove.util;

import java.util.List;

import com.google.gson.Gson;
import com.ibm.findyourlove.model.Person;
import com.ibm.findyourlove.model.QueryPara;

public class JsonUtils 
{
	public static String getJson(Person p)
	{
		Gson gson = new Gson();
		String json = gson.toJson(p);
		return json;
	}
	
	public static Person getPersonFromJson(String json)
	{
		Gson gson = new Gson();
		Person p = gson.fromJson(json, Person.class);
		return p;
	}
	
	
	public static QueryPara getQueryPara(String json){
		Gson gson = new Gson();
		QueryPara queryPara=gson.fromJson(json, QueryPara.class);
		return queryPara;
	}
	
	public static String getListPersonJson(List<Person> people)
	{
		Gson gson = new Gson();
		String json = gson.toJson(people);
		return json;
	}
	
}
