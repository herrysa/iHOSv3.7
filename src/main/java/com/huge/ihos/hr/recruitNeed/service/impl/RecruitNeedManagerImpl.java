package com.huge.ihos.hr.recruitNeed.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.recruitNeed.dao.RecruitNeedDao;
import com.huge.ihos.hr.recruitNeed.model.RecruitNeed;
import com.huge.ihos.hr.recruitNeed.service.RecruitNeedManager;
import com.huge.ihos.hr.recruitPlan.dao.RecruitPlanDao;
import com.huge.ihos.hr.recruitPlan.model.RecruitPlan;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("recruitNeedManager")
public class RecruitNeedManagerImpl extends GenericManagerImpl<RecruitNeed, String> implements RecruitNeedManager {
    private RecruitNeedDao recruitNeedDao;
    private RecruitPlanDao recruitPlanDao;
    private BillNumberManager billNumberManager;

    @Autowired
    public RecruitNeedManagerImpl(RecruitNeedDao recruitNeedDao) {
        super(recruitNeedDao);
        this.recruitNeedDao = recruitNeedDao;
    }
    
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager){
    	this.billNumberManager=billNumberManager;
    }
    @Autowired
    public void setRecruitPlanDao(RecruitPlanDao recruitPlanDao) {
		this.recruitPlanDao = recruitPlanDao;
	}
    public JQueryPager getRecruitNeedCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return recruitNeedDao.getRecruitNeedCriteria(paginatedList,filters);
	}
    @Override
    public void auditRecruitNeed(List<String> checkIds,Person person,Date date){
    	RecruitNeed recruitNeed = null;
		for(String checkId:checkIds){
			recruitNeed = recruitNeedDao.get(checkId);
			recruitNeed.setState("2");
			recruitNeed.setChecker(person);
			recruitNeed.setCheckDate(date);
			recruitNeedDao.save(recruitNeed);
		}
    }
    @Override
	public void antiRecruitNeed(List<String> cancelCheckIds) {
    	RecruitNeed recruitNeed = null;
		for(String cancelCheckId:cancelCheckIds){
			recruitNeed = recruitNeedDao.get(cancelCheckId);
			recruitNeed.setState("1");
			recruitNeed.setChecker(null);
			recruitNeed.setCheckDate(null);
			recruitNeedDao.save(recruitNeed);		
		}
	}
    @Override
    public void addPlanRecruitNeed(List<String> addPlanCheckIds,Person person,Date date,String yearMonth){
    	RecruitNeed recruitNeed = null;
    	RecruitPlan recruitPlan = null;
		for(String addPlanCheckId:addPlanCheckIds){
			recruitNeed = recruitNeedDao.get(addPlanCheckId);
			recruitPlan=new RecruitPlan();
			recruitPlan.setAgeEnd(recruitNeed.getAgeEnd());
			recruitPlan.setAgeStart(recruitNeed.getAgeStart());
//			recruitPlan.setCode(recruitNeed.getCode());
			recruitPlan.setEducationLevel(recruitNeed.getEducationLevel());
			recruitPlan.setEndDate(recruitNeed.getEndDate());
			recruitPlan.setJobRequirements(recruitNeed.getJobRequirements());
			recruitPlan.setMaritalStatus(recruitNeed.getMaritalStatus());
			recruitPlan.setName(recruitNeed.getName());
			recruitPlan.setOrgCode(recruitNeed.getHrDept().getOrgCode());
			recruitPlan.setPoliticsStatus(recruitNeed.getPoliticsStatus());
//			recruitPlan.setPost(recruitNeed.getPost());
			recruitPlan.setPostResponsibility(recruitNeed.getPostResponsibility());
			recruitPlan.setProfession(recruitNeed.getProfession());
			recruitPlan.setRecruitNumber(recruitNeed.getRecruitNumber());
			recruitPlan.setSex(recruitNeed.getSex());
			recruitPlan.setStartDate(recruitNeed.getStartDate());
			recruitPlan.setState("1");
			recruitPlan.setWorkExperience(recruitNeed.getWorkExperience());
			recruitPlan.setWorkplace(recruitNeed.getWorkplace());
			recruitPlan.setMakeDate(date);
			recruitPlan.setMaker(person);
			recruitPlan.setYearMonth(yearMonth);
			recruitPlan.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_PLAN, yearMonth));
//			recruitPlan.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_PLAN,recruitPlan.getYearMonth()));
			recruitPlanDao.save(recruitPlan);
		}
    }
    @Override
    public RecruitNeed saveRecruitNeed(RecruitNeed recruitNeed,Boolean isEntityIsNew){
    	 if(isEntityIsNew){
    		 recruitNeed.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_RECRUIT_NEED,recruitNeed.getYearMonth()));
		 }
    	 recruitNeed=recruitNeedDao.save(recruitNeed);
    	 return recruitNeed;
    }
}