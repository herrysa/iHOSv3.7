package com.huge.ihos.system.configuration.proj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ProjUse table.
 */
public interface ProjUseDao extends GenericDao<ProjUse, String> {
	public JQueryPager getProjUseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ProjUse> getAllEnabled();

}