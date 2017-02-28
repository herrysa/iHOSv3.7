package com.huge.ihos.bm.budgetAssistType.service;


import java.util.List;
import com.huge.ihos.bm.budgetAssistType.model.BudgetAssistType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetAssistTypeManager extends GenericManager<BudgetAssistType, String> {
     public JQueryPager getBudgetAssistTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}