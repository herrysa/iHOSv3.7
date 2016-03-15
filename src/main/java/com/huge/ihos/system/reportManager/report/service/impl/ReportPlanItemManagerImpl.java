package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.ReportPlanItemDao;
import com.huge.ihos.system.reportManager.report.model.ReportPlanItem;
import com.huge.ihos.system.reportManager.report.service.ReportPlanItemManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("reportPlanItemManager")
public class ReportPlanItemManagerImpl extends GenericManagerImpl<ReportPlanItem, String> implements ReportPlanItemManager {
    private ReportPlanItemDao reportPlanItemDao;

    @Autowired
    public ReportPlanItemManagerImpl(ReportPlanItemDao reportPlanItemDao) {
        super(reportPlanItemDao);
        this.reportPlanItemDao = reportPlanItemDao;
    }
    
    public JQueryPager getReportPlanItemCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return reportPlanItemDao.getReportPlanItemCriteria(paginatedList,filters);
	}
}