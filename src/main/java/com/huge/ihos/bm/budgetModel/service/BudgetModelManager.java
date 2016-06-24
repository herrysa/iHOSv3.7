package com.huge.ihos.bm.budgetModel.service;


import java.util.List;
import com.huge.ihos.bm.budgetModel.model.BudgetModel;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BudgetModelManager extends GenericManager<BudgetModel, String> {
     public JQueryPager getBudgetModelCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}