package com.huge.ihos.system.reportManager.search.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.search.dao.ReportDao;
import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.ihos.system.reportManager.search.service.ReportManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service( "reportManager" )
public class ReportManagerImpl
    extends GenericManagerImpl<Report, Long>
    implements ReportManager {
    ReportDao reportDao;

    @Autowired
    public ReportManagerImpl( ReportDao reportDao ) {
        super( reportDao );
        this.reportDao = reportDao;
    }

    public JQueryPager getReportCriteria( JQueryPager paginatedList,List<PropertyFilter> filters ) {
        return reportDao.getReportCriteria( paginatedList,filters );
    }

    public List getReportByGroup( String groupName ) {
        return reportDao.getReportByGroup( groupName );
    }

}