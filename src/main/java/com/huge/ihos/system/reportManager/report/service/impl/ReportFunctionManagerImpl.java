package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.ReportFunctionDao;
import com.huge.ihos.system.reportManager.report.model.ReportFunction;
import com.huge.ihos.system.reportManager.report.service.ReportFunctionManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportFunctionManager")
public class ReportFunctionManagerImpl extends GenericManagerImpl<ReportFunction, String> implements ReportFunctionManager {
    private ReportFunctionDao reportFunctionDao;

    @Autowired
    public ReportFunctionManagerImpl(ReportFunctionDao reportFunctionDao) {
        super(reportFunctionDao);
        this.reportFunctionDao = reportFunctionDao;
    }
    
    public JQueryPager getReportFunctionCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportFunctionDao.getReportFunctionCriteria(paginatedList,filters);
	}
}