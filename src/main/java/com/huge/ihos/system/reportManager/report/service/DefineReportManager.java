package com.huge.ihos.system.reportManager.report.service;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DefineReportManager extends GenericManager<DefineReport, String> {
     public JQueryPager getDefineReportCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public void getFuncValue(List<ReportFunc> funcList);
}