package com.huge.ihos.bm.index.service;


import java.util.List;
import com.huge.ihos.bm.index.model.BudgetIndex;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetIndexManager extends GenericManager<BudgetIndex, String> {
     public JQueryPager getBudgetIndexCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}