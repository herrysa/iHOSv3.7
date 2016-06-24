package com.huge.ihos.bm.budgetUpdata.dao;


import java.util.List;

import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetUpdata table.
 */
public interface BudgetUpdataDao extends GenericDao<BudgetUpdata, String> {
	public JQueryPager getBudgetUpdataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}