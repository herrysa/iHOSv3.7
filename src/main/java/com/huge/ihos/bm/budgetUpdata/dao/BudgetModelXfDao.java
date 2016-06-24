package com.huge.ihos.bm.budgetUpdata.dao;


import java.util.List;

import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetModelXf table.
 */
public interface BudgetModelXfDao extends GenericDao<BudgetModelXf, String> {
	public JQueryPager getBudgetModelXfCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}