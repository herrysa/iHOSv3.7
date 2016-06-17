package com.huge.ihos.bm.budgetType.service;


import java.util.List;
import com.huge.ihos.bm.budgetType.model.BudgetType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetTypeManager extends GenericManager<BudgetType, String> {
     public JQueryPager getBudgetTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}