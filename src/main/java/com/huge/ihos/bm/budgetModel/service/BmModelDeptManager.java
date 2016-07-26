package com.huge.ihos.bm.budgetModel.service;


import java.util.List;
import com.huge.ihos.bm.budgetModel.model.BmModelDept;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmModelDeptManager extends GenericManager<BmModelDept, String> {
     public JQueryPager getBmModelDeptCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}