package com.huge.ihos.system.configuration.proj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ProjType table.
 */
public interface ProjTypeDao extends GenericDao<ProjType, String> {
	public JQueryPager getProjTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ProjType> getAllEnabled();

}