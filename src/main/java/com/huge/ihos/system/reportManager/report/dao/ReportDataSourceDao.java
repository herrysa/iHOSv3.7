package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportDataSource;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportDataSource table.
 */
public interface ReportDataSourceDao extends GenericDao<ReportDataSource, String> {
	public JQueryPager getReportDataSourceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}