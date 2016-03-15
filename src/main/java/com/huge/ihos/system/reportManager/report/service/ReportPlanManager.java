package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import java.util.Map;

import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportPlan;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportPlanManager extends GenericManager<ReportPlan, String> {
     public JQueryPager getReportPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 报表数据查询
      * @param columns
      * @param sqlFilterList
      * @param reportDefine
      * @param groupFilterMap
      * @return
      */
     public List<Map<String,Object>> getReportPlanGridData(String columns,List<String> sqlFilterList,ReportDefine reportDefine,Map<String,String> groupFilterMap,List<ReportType> reportTypes) throws Exception;
     /**
      * 删除方案
      * @param planId
      */
     public void removeReportPlan(String planId);
     /**
      * 保存方案
      * @param reportPlan
      * @param reportPlanItemsStr
      * @param eportPlanFilterStr
      * @return reportPlan
      * @throws Exception
      */
     public ReportPlan saveReportPlan(ReportPlan reportPlan,String reportPlanItemsStr,String reportPlanFilterStr) throws Exception;
 	/**
 	 * 反转查询
 	 * @param items
 	 * @param sqlFilterList
 	 * @param reportDefine
 	 * @return
 	 * @throws Exception
 	 */
 	public List<Map<String,Object>> getReportPlanReverseGridData(List<ReportItem> items,List<String> sqlFilterList,ReportDefine reportDefine) throws Exception;
}