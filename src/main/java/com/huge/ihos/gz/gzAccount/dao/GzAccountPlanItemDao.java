package com.huge.ihos.gz.gzAccount.dao;


import java.util.List;

import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzAccountPlanItem table.
 */
public interface GzAccountPlanItemDao extends GenericDao<GzAccountPlanItem, String> {
	public JQueryPager getGzAccountPlanItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}