package com.huge.ihos.hr.pact.dao;


import java.util.List;

import com.huge.ihos.hr.pact.model.PactRelieve;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PactRelieve table.
 */
public interface PactRelieveDao extends GenericDao<PactRelieve, String> {
	public JQueryPager getPactRelieveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}