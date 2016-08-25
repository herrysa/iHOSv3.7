package com.huge.ihos.bm.budgetModel.dao;


import java.util.List;

import com.huge.ihos.bm.budgetModel.model.BmDepartment;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmDepartment table.
 */
public interface BmDepartmentDao extends GenericDao<BmDepartment, String> {
	public JQueryPager getBmDepartmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}