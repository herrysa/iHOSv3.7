package com.huge.ihos.hr.pact.dao;


import java.util.List;

import com.huge.ihos.hr.pact.model.Pact;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Pact table.
 */
public interface PactDao extends GenericDao<Pact, String> {
	public JQueryPager getPactCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}