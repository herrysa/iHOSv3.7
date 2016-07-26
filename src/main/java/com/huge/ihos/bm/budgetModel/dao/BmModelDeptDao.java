package com.huge.ihos.bm.budgetModel.dao;


import java.util.List;

import com.huge.ihos.bm.budgetModel.model.BmModelDept;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BmModelDept table.
 */
public interface BmModelDeptDao extends GenericDao<BmModelDept, String> {
	public JQueryPager getBmModelDeptCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}