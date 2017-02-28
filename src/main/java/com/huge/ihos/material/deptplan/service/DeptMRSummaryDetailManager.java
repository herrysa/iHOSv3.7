package com.huge.ihos.material.deptplan.service;


import java.util.List;
import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptMRSummaryDetailManager extends GenericManager<DeptMRSummaryDetail, String> {
     public JQueryPager getDeptMRSummaryDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}