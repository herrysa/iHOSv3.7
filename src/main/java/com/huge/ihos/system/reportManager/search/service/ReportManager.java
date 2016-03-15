package com.huge.ihos.system.reportManager.search.service;

import java.util.List;

import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ReportManager
    extends GenericManager<Report, Long> {
    public JQueryPager getReportCriteria( final JQueryPager paginatedList,List<PropertyFilter> filters );

    public List getReportByGroup( String groupName );
}