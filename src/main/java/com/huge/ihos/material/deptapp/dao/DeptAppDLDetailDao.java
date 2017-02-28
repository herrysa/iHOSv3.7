package com.huge.ihos.material.deptapp.dao;


import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptAppDLDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptAppDLDetail table.
 */
public interface DeptAppDLDetailDao extends GenericDao<DeptAppDLDetail, String> {
	public JQueryPager getDeptAppDLDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}