package com.huge.ihos.hr.pact.dao;


import java.util.List;

import com.huge.ihos.hr.pact.model.PactRenew;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PactRenew table.
 */
public interface PactRenewDao extends GenericDao<PactRenew, String> {
	public JQueryPager getPactRenewCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}