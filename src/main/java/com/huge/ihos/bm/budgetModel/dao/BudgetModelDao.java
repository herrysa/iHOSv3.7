package com.huge.ihos.bm.budgetModel.dao;


import java.util.List;

import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetModel table.
 */
public interface BudgetModelDao extends GenericDao<BudgetModel, String> {
	public JQueryPager getBudgetModelCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}