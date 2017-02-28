package com.huge.ihos.material.deptplan.service.impl;

import java.util.List;

import com.huge.ihos.material.deptplan.dao.DeptNeedPlanDetailDao;
import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.ihos.material.deptplan.service.DeptNeedPlanDetailManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("deptNeedPlanDetailManager")
public class DeptNeedPlanDetailManagerImpl extends GenericManagerImpl<DeptNeedPlanDetail, String> implements DeptNeedPlanDetailManager {
    private DeptNeedPlanDetailDao deptNeedPlanDetailDao;

    @Autowired
    public DeptNeedPlanDetailManagerImpl(DeptNeedPlanDetailDao deptNeedPlanDetailDao) {
        super(deptNeedPlanDetailDao);
        this.deptNeedPlanDetailDao = deptNeedPlanDetailDao;
    }
    
    public JQueryPager getDeptNeedPlanDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return deptNeedPlanDetailDao.getDeptNeedPlanDetailCriteria(paginatedList,filters);
	}
    @Override
	public Double getLastAmount(String periodMonth,String storeId,String deptId,String invId){
		String year = periodMonth.substring(0, 4);
		int yearint = Integer.parseInt(year);
		String month = periodMonth.substring(4,6);
		int monthint = Integer.parseInt(month);
		if(monthint==1){
			yearint = yearint - 1;
			year = yearint +"";
			month = "12";
		}else{
			monthint = monthint - 1;
			year = yearint +"";
			if(monthint <10){
				month = "0" + monthint;
			}else{
				month = "" + monthint;
			}
		}
		String lastPeriodMonth = year + month;
		return deptNeedPlanDetailDao.getLastAmount(lastPeriodMonth, storeId, deptId,invId);
	}
    @Override
    public Double getsumAmount(String periodMonth,String storeId,String deptId,String invId){
		return deptNeedPlanDetailDao.getLastAmount(periodMonth, storeId, deptId,invId);
    }
}