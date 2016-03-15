package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportPlanItem table.
 */
public interface ReportPlanItemDao extends GenericDao<ReportPlanItem, String> {
	public JQueryPager getReportPlanItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}