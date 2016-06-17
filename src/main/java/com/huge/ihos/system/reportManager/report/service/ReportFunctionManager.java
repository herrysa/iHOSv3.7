package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportFunctionManager extends GenericManager<ReportFunction, String> {
     public JQueryPager getReportFunctionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}