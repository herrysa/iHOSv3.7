package com.huge.ihos.gz.gzAccount.dao;


import java.util.List;

import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzAccountPlanFliter table.
 */
public interface GzAccountPlanFilterDao extends GenericDao<GzAccountPlanFilter, String> {
	public JQueryPager getGzAccountPlanFliterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}