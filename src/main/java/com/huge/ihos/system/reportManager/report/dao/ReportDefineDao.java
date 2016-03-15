package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ReportDefine table.
 */
public interface ReportDefineDao extends GenericDao<ReportDefine, String> {
	public JQueryPager getReportDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}