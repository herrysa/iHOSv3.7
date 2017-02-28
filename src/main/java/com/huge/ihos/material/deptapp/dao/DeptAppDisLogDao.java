package com.huge.ihos.material.deptapp.dao;


import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptAppDisLog table.
 */
public interface DeptAppDisLogDao extends GenericDao<DeptAppDisLog, String> {
	public JQueryPager getDeptAppDisLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}