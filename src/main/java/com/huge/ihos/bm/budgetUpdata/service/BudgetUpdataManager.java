package com.huge.ihos.bm.budgetUpdata.service;


import java.util.List;
import com.huge.ihos.bm.budgetUpdata.model.BudgetUpdata;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetUpdataManager extends GenericManager<BudgetUpdata, String> {
     public JQueryPager getBudgetUpdataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}