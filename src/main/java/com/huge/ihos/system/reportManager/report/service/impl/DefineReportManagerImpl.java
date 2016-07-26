package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.reportManager.report.dao.DefineReportDao;
import com.huge.ihos.system.reportManager.report.dao.hibernate.BathFuncThread;
import com.huge.ihos.system.reportManager.report.model.DefineReport;
import com.huge.ihos.system.reportManager.report.model.ReportFunc;
import com.huge.ihos.system.reportManager.report.service.DefineReportManager;
import com.huge.service.impl.GenericManagerImpl;
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

	@Override
	public void getFuncValue(List<ReportFunc> funcList) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10); 
		for(ReportFunc reportFunc : funcList){
			BathFuncThread bathFuncThread = new BathFuncThread(reportFunc);
			fixedThreadPool.execute(bathFuncThread);
		}
		fixedThreadPool.shutdown();
		try {
			while(!fixedThreadPool.awaitTermination(1000, TimeUnit.MILLISECONDS)){
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
    
}