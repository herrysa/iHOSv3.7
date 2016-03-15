package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.ReportPlanFilterDao;
import com.huge.ihos.system.reportManager.report.model.ReportPlanFilter;
import com.huge.ihos.system.reportManager.report.service.ReportPlanFilterManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportPlanFilterManager")
public class ReportPlanFilterManagerImpl extends GenericManagerImpl<ReportPlanFilter, String> implements ReportPlanFilterManager {
    private ReportPlanFilterDao reportPlanFilterDao;

    @Autowired
    public ReportPlanFilterManagerImpl(ReportPlanFilterDao reportPlanFilterDao) {
        super(reportPlanFilterDao);
        this.reportPlanFilterDao = reportPlanFilterDao;
    }
    
    public JQueryPager getReportPlanFilterCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportPlanFilterDao.getReportPlanFilterCriteria(paginatedList,filters);
	}
}