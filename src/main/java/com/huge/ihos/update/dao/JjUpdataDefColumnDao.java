package com.huge.ihos.update.dao;


import java.util.List;

import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the JjUpdataDefColumn table.
 */
public interface JjUpdataDefColumnDao extends GenericDao<JjUpdataDefColumn, Long> {
	public JQueryPager getJjUpdataDefColumnCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	public List<JjUpdataDefColumn> getEnabledByOrder();
}