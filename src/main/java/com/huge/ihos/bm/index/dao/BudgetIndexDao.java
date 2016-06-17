package com.huge.ihos.bm.index.dao;


import java.util.List;

import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BudgetIndex table.
 */
public interface BudgetIndexDao extends GenericDao<BudgetIndex, String> {
	public JQueryPager getBudgetIndexCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}