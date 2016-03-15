package com.huge.ihos.hr.pact.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.pact.model.PactBreak;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PactBreakManager extends GenericManager<PactBreak, String> {
     public JQueryPager getPactBreakCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void addPactBreak(String pactIds, PactBreak pactBreak);
	 /**
	  * 确认终止合同
	  * @param idl
	  * @param date
	  * @param person
	  */
	 public void confirmBreak(String pactIds, PactBreak pactBreak, Date date, Person person);
}