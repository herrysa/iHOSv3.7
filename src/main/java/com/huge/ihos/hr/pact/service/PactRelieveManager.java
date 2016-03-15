package com.huge.ihos.hr.pact.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.pact.model.PactRelieve;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PactRelieveManager extends GenericManager<PactRelieve, String> {
     public JQueryPager getPactRelieveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void addPactRelieve(String pactIds, PactRelieve pactRelieve);
	 /**
	  * 确认解除合同
	  * @param idl
	  * @param date
	  * @param person
	  */
	 public void confirmRelieve(String pactIds, PactRelieve pactRelieve, Date date, Person person);
}