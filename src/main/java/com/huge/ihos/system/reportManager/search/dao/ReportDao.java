package com.huge.ihos.system.reportManager.search.dao;

import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

/**
 * An interface that provides a data management interface to the Report table.
 */
public interface ReportDao
    extends GenericDao<Report, Long> {

    public JQueryPager getReportCriteria( final JQueryPager paginatedList,List<PropertyFilter> filters );

    public List getReportByGroup( String groupName );
}