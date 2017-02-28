package com.huge.ihos.material.deptplan.service;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptMRSummaryManager extends GenericManager<DeptMRSummary, String> {
     public JQueryPager getDeptMRSummaryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public DeptMRSummary saveDeptMRSummary(DeptMRSummary deptMRSummary,DeptMRSummaryDetail[] deptMRSummaryDetails);
}