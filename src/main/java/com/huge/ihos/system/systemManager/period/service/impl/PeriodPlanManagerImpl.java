package com.huge.ihos.system.systemManager.period.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.period.dao.PeriodPlanDao;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.ihos.system.systemManager.period.service.PeriodPlanManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("periodPlanManager")
public class PeriodPlanManagerImpl extends GenericManagerImpl<PeriodPlan, String> implements PeriodPlanManager {
    private PeriodPlanDao periodPlanDao;

    @Autowired
    public PeriodPlanManagerImpl(PeriodPlanDao periodPlanDao) {
        super(periodPlanDao);
        this.periodPlanDao = periodPlanDao;
    }
    
    public JQueryPager getPeriodPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return periodPlanDao.getPeriodPlanCriteria(paginatedList,filters);
	}

    @Override
    public PeriodPlan getDefaultPeriodPlan() {
    	return periodPlanDao.getDefaultPeriodPlan();
    }
}