package com.huge.ihos.system.reportManager.report.dao;


import java.util.List;

import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the UserReportDefine table.
 */
public interface UserReportDefineDao extends GenericDao<UserReportDefine, String> {
	public JQueryPager getUserReportDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}