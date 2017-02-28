package com.huge.ihos.system.configuration.proj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ProjNature table.
 */
public interface ProjNatureDao extends GenericDao<ProjNature, String> {
	public JQueryPager getProjNatureCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ProjNature> getAllEnabled();

}