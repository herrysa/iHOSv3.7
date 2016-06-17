package com.huge.ihos.system.reportManager.report.service.impl;

import java.util.ArrayList;
import java.util.List;

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
		int thredNum = 10;
		double eachLength = Math.ceil(funcList.size()/10);
		List<Thread> threads = new ArrayList<Thread>();
		for(int i=0;i<thredNum;i++){
			BathFuncThread bathFuncThread = new BathFuncThread((int)(i*eachLength), (int)((i+1)*eachLength-1), funcList);
			Thread thread = new Thread(bathFuncThread);
			thread.start();
			threads.add(thread);
		}
		for(Thread thread : threads){
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		/*for(ReportFunc reportFunc : funcList){
			defineReportDao.getFuncValue(reportFunc);
		}*/
		
	}
    
}