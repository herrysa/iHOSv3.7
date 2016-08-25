package com.huge.ihos.bm.budgetModel.service;


import java.util.List;
import com.huge.ihos.bm.budgetModel.model.BmDepartment;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BmDepartmentManager extends GenericManager<BmDepartment, String> {
     public JQueryPager getBmDepartmentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}