package com.huge.ihos.material.order.dao;


import java.util.List;

import com.huge.ihos.material.order.model.ImportOrderLog;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ImportOrderLog table.
 */
public interface ImportOrderLogDao extends GenericDao<ImportOrderLog, String> {
	public JQueryPager getImportOrderLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}