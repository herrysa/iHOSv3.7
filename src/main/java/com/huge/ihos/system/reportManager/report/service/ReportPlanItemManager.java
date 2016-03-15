package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportPlanItemManager extends GenericManager<ReportPlanItem, String> {
     public JQueryPager getReportPlanItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}