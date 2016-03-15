package com.huge.ihos.hr.recruitPlan.service.impl;

import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.ihos.hr.recruitNeed.service.RecruitNeedManager;
import com.huge.ihos.hr.recruitPlan.dao.RecruitPlanDao;
import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.hr.recruitPlan.service.RecruitPlanManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("recruitPlanManager")
public class RecruitPlanManagerImpl extends GenericManagerImpl<RecruitPlan, String> implements RecruitPlanManager {
    private RecruitPlanDao recruitPlanDao;
    private BillNumberManager billNumberManager;
    private RecruitNeedManager recruitNeedManager;

    @Autowired
    public RecruitPlanManagerImpl(RecruitPlanDao recruitPlanDao) {
        super(recruitPlanDao);
        this.recruitPlanDao = recruitPlanDao;
    }
    @Autowired
    public void setRecruitNeedManager(RecruitNeedManager recruitNeedManager) {
		this.recruitNeedManager = recruitNeedManager;
	}
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getRecruitPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return recruitPlanDao.getRecruitPlanCriteria(paginatedList,filters);
	}
    @Override
    public void auditRecruitPlan(List<String> checkIds,Person person,Date date){
    	RecruitPlan recruitPlan = null;
		for(String checkId:checkIds){
			recruitPlan = recruitPlanDao.get(checkId);
			recruitPlan.setState("2");
			recruitPlan.setChecker(person);
			recruitPlan.setCheckDate(date);
			recruitPlanDao.save(recruitPlan);
		}
    }
    @Override
	public void antiRecruitPlan(List<String> cancelCheckIds) {
    	RecruitPlan recruitPlan = null;
		for(String cancelCheckId:cancelCheckIds){
			recruitPlan = recruitPlanDao.get(cancelCheckId);
			recruitPlan.setState("1");
			recruitPlan.setChecker(null);
			recruitPlan.setCheckDate(null);
			recruitPlanDao.save(recruitPlan);	
		}
	}
    @Override
    public void confirmRecruitPlan(List<String> confirmCheckIds,Person person,Date date){
    	RecruitPlan recruitPlan = null;
		for(String confirmId:confirmCheckIds){
			recruitPlan = recruitPlanDao.get(confirmId);
			recruitPlan.setReleaseder(person);
			recruitPlan.setReleasedDate(date);
			recruitPlan.setBreaker(null);
			recruitPlan.setBreakDate(null);
			recruitPlan.setState("3");
			recruitPlanDao.save(recruitPlan);
		}
    }
    @Override
    public void breakRecruitPlan(List<String> breakIds,Person person,Date date){
    	RecruitPlan recruitPlan = null;
		for(String breakId:breakIds){
			recruitPlan = recruitPlanDao.get(breakId);
			recruitPlan.setBreaker(person);
			recruitPlan.setBreakDate(date);
			recruitPlan.setState("4");
			recruitPlanDao.save(recruitPlan);
		}
    }
    @Override
    public RecruitPlan saveRecruitPlan(RecruitPlan recruitPlan,Boolean isEntityIsNew,Person person){
    	 if(isEntityIsNew){
			 recruitPlan.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_PLAN,recruitPlan.getYearMonth()));
			 String needIds=recruitPlan.getNeedIds();
			 Date date=new Date();
			 StringTokenizer ids = new StringTokenizer(needIds,",");
			 RecruitNeed recruitNeed =null;
				while (ids.hasMoreTokens()) {
					String needId = ids.nextToken();
					recruitNeed=recruitNeedManager.get(needId);
					recruitNeed.setState("3");
					recruitNeed.setDoneDate(date);
					recruitNeed.setDoner(person);
					recruitNeedManager.save(recruitNeed);
				}
		 }
    	 recruitPlan=recruitPlanDao.save(recruitPlan);
    	 return recruitPlan;
    }
    @Override
    public void deleteRecruitPlan(List<String> delIds){
    	RecruitPlan recruitPlan = null;
		for(String delId:delIds){
			recruitPlan = recruitPlanDao.get(delId);
			String needIds=recruitPlan.getNeedIds();
			 StringTokenizer ids = new StringTokenizer(needIds,",");
			 RecruitNeed recruitNeed =null;
				while (ids.hasMoreTokens()) {
					String needId = ids.nextToken();
					recruitNeed=recruitNeedManager.get(needId);
					recruitNeed.setState("2");
					recruitNeed.setDoneDate(null);
					recruitNeed.setDoner(null);
					recruitNeedManager.save(recruitNeed);
				}
			recruitPlanDao.remove(delId);
		}
    }
}