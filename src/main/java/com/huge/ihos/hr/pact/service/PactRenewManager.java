package com.huge.ihos.hr.pact.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.pact.model.PactRenew;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PactRenewManager extends GenericManager<PactRenew, String> {
     public JQueryPager getPactRenewCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	 public void addPactRenew(String pactIds, PactRenew pactRenew);

	 public void confirmRenew(String pactIds,PactRenew pactRenew, Date operTime, Person person);
}