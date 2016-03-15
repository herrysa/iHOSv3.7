package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.model.ReportItem;
import com.huge.ihos.system.reportManager.report.model.ReportPlan;
import com.huge.ihos.system.reportManager.report.model.ReportType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportPlan table.
 */
public interface ReportPlanDao extends GenericDao<ReportPlan, String> {
	public JQueryPager getReportPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	/**
	 * 报表数据查询
	 * @param columns
	 * @param sqlFilterList
	 * @param reportDefine
	 * @param groupFilterMap
	 * @return
	 */
	public List<Map<String,Object>> getReportPlanGridData(String columns,List<String> sqlFilterList,ReportDefine reportDefine,Map<String,String> groupFilterMap,List<ReportType> reportTypes)  throws Exception;
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