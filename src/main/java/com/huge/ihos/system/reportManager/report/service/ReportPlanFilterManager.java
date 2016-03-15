package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportPlanFilterManager extends GenericManager<ReportPlanFilter, String> {
     public JQueryPager getReportPlanFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}