package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportPlanFilter table.
 */
public interface ReportPlanFilterDao extends GenericDao<ReportPlanFilter, String> {
	public JQueryPager getReportPlanFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}