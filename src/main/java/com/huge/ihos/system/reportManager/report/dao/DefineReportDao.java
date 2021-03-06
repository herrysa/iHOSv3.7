package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DefineReport table.
 */
public interface DefineReportDao extends GenericDao<DefineReport, String> {
	public JQueryPager getDefineReportCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public void getFuncValue(ReportFunc reportFunc);
}