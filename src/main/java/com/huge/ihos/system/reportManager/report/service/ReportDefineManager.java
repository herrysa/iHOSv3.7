package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportDefineManager extends GenericManager<ReportDefine, String> {
     public JQueryPager getReportDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}