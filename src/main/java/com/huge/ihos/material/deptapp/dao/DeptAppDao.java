package com.huge.ihos.material.deptapp.dao;

import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptApp;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptApp table.
 */
public interface DeptAppDao extends GenericDao<DeptApp, String> {
	public JQueryPager getDeptAppCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}