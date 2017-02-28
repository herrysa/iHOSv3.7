package com.huge.ihos.bm.budgetAssistType.dao;


import java.util.List;

import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetAssistType table.
 */
public interface BudgetAssistTypeDao extends GenericDao<BudgetAssistType, String> {
	public JQueryPager getBudgetAssistTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}