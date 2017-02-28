package com.huge.ihos.bm.budgetModel.service;


import java.util.List;
import com.huge.ihos.bm.budgetModel.model.BmModelAssist;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmModelAssistManager extends GenericManager<BmModelAssist, String> {
     public JQueryPager getBmModelAssistCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}