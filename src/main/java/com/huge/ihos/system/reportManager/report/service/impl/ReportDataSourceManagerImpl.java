package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.ReportDataSourceDao;
import com.huge.ihos.system.reportManager.report.model.ReportDataSource;
import com.huge.ihos.system.reportManager.report.service.ReportDataSourceManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportDataSourceManager")
public class ReportDataSourceManagerImpl extends GenericManagerImpl<ReportDataSource, String> implements ReportDataSourceManager {
    private ReportDataSourceDao reportDataSourceDao;

    @Autowired
    public ReportDataSourceManagerImpl(ReportDataSourceDao reportDataSourceDao) {
        super(reportDataSourceDao);
        this.reportDataSourceDao = reportDataSourceDao;
    }
    
    public JQueryPager getReportDataSourceCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportDataSourceDao.getReportDataSourceCriteria(paginatedList,filters);
	}
}