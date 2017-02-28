package com.huge.ihos.system.configuration.proj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Proj table.
 */
public interface ProjDao extends GenericDao<Proj, String> {
	public JQueryPager getProjCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}