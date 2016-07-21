package com.huge.ihos.bm.budgetModel.service;


import java.util.List;
import com.huge.ihos.bm.budgetModel.model.BmModelProcessLog;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmModelProcessLogManager extends GenericManager<BmModelProcessLog, String> {
     public JQueryPager getBmModelProcessLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}