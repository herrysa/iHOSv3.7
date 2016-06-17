package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.ReportDataSource;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportDataSourceManager extends GenericManager<ReportDataSource, String> {
     public JQueryPager getReportDataSourceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}