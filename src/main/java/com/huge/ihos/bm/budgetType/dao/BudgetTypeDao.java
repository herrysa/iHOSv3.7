package com.huge.ihos.bm.budgetType.dao;


import java.util.List;

import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetType table.
 */
public interface BudgetTypeDao extends GenericDao<BudgetType, String> {
	public JQueryPager getBudgetTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}