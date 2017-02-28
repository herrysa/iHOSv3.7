package com.huge.ihos.material.deptapp.dao;

import java.util.List;

import com.huge.ihos.material.deptapp.model.DeptAppDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptAppDetail table.
 */
public interface DeptAppDetailDao extends GenericDao<DeptAppDetail, String> {
	public JQueryPager getDeptAppDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}