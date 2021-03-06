package com.huge.ihos.bm.budgetUpdata.service;


import java.util.List;
import com.huge.ihos.bm.budgetUpdata.model.BudgetModelXf;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetModelXfManager extends GenericManager<BudgetModelXf, String> {
     public JQueryPager getBudgetModelXfCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}