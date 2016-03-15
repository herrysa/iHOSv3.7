package com.huge.ihos.hr.query.dao;


import java.util.List;

import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the QueryCommon table.
 */
public interface QueryCommonDao extends GenericDao<QueryCommon, String> {
	public JQueryPager getQueryCommonCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}