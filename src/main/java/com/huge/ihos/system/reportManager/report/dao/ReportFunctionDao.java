package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportFunction table.
 */
public interface ReportFunctionDao extends GenericDao<ReportFunction, String> {
	public JQueryPager getReportFunctionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}