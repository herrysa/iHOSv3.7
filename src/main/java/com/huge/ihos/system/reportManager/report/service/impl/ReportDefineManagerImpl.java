package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.ReportDefineDao;
import com.huge.ihos.system.reportManager.report.model.ReportDefine;
import com.huge.ihos.system.reportManager.report.service.ReportDefineManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportDefineManager")
public class ReportDefineManagerImpl extends GenericManagerImpl<ReportDefine, String> implements ReportDefineManager {
    private ReportDefineDao reportDefineDao;

    @Autowired
    public ReportDefineManagerImpl(ReportDefineDao reportDefineDao) {
        super(reportDefineDao);
        this.reportDefineDao = reportDefineDao;
    }
    
    public JQueryPager getReportDefineCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportDefineDao.getReportDefineCriteria(paginatedList,filters);
	}
}