package com.ibm.personafusion.controller;

import java.util.List;

import com.ibm.personafusion.db.ICloudantClient;
import com.ibm.personafusion.model.Person;

/** 
 * @author yzyangbj@cn.ibm.com
 * @version time：May 17, 2015 2:26:08 PM 
 * Description:
 * Used for test the business layer
 * TODO test tasks
 * Test1: test watson personal insights method
 * Test2: test persona controller method
 * After test config RESTFULL API
 */
public class CloudantClientImpl implements ICloudantClient {

	@Override
	public Person getPerson(String personId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Person> getPersonSet(String query) {
		// TODO Auto-generated method stub
		return null;
	}

}
