package com.huge.ihos.hr.pact.dao;


import java.util.List;

import com.huge.ihos.hr.pact.model.PactBreak;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PactBreak table.
 */
public interface PactBreakDao extends GenericDao<PactBreak, String> {
	public JQueryPager getPactBreakCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}