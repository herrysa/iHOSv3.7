package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import com.huge.ihos.system.reportManager.report.dao.DefineReportDao;
import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.service.DefineReportManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("defineReportManager")
public class DefineReportManagerImpl extends GenericManagerImpl<DefineReport, String> implements DefineReportManager {
    private DefineReportDao defineReportDao;

    @Autowired
    public DefineReportManagerImpl(DefineReportDao defineReportDao) {
        super(defineReportDao);
        this.defineReportDao = defineReportDao;
    }
    
    public JQueryPager getDefineReportCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return defineReportDao.getDefineReportCriteria(paginatedList,filters);
	}
}