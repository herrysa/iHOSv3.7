package com.huge.ihos.hr.trainNeed.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainNeed.dao.TrainNeedDao;
import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.hr.trainNeed.service.TrainNeedManager;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainPlan.service.TrainPlanManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainNeedManager")
public class TrainNeedManagerImpl extends GenericManagerImpl<TrainNeed, String> implements TrainNeedManager {
    private TrainNeedDao trainNeedDao;
    private BillNumberManager billNumberManager;
    private TrainPlanManager trainPlanManager;

    @Autowired
    public TrainNeedManagerImpl(TrainNeedDao trainNeedDao) {
        super(trainNeedDao);
        this.trainNeedDao = trainNeedDao;
    }
    @Autowired
    public void setTrainPlanManager(TrainPlanManager trainPlanManager) {
		this.trainPlanManager = trainPlanManager;
	}
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getTrainNeedCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainNeedDao.getTrainNeedCriteria(paginatedList,filters);
	}
    @Override
    public void auditTrainNeed(List<String> checkIds,Person person,Date date){
    	TrainNeed trainNeed = null;
		for(String checkId:checkIds){
			trainNeed = trainNeedDao.get(checkId);
			trainNeed.setState("2");
			trainNeed.setChecker(person);
			trainNeed.setCheckDate(date);
			trainNeedDao.save(trainNeed);
		}
    }
    @Override
	public void antiTrainNeed(List<String> cancelCheckIds) {
    	TrainNeed trainNeed = null;
		for(String cancelCheckId:cancelCheckIds){
			trainNeed = trainNeedDao.get(cancelCheckId);
			trainNeed.setState("1");
			trainNeed.setChecker(null);
			trainNeed.setCheckDate(null);
			trainNeedDao.save(trainNeed);		
		}
	}
    @Override
    public TrainNeed saveTrainNeed(TrainNeed trainNeed,Boolean isEntityIsNew){
    	String personNames = trainNeed.getPersonNames();
		if("".equals(personNames.trim())){
			trainNeed.setPeopleNumber(0);
		}else{
			trainNeed.setPeopleNumber(personNames.split(",").length);
		}
		if(isEntityIsNew){
			trainNeed.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_TRAIN_CLASS,trainNeed.getYearMonth()));
		}
		trainNeed=trainNeedDao.save(trainNeed);
		String planId=trainNeed.getTrainPlan().getId();
		TrainPlan trainPlan= trainPlanManager.get(planId);
		trainPlan.setState("3");
		trainPlanManager.save(trainPlan);
		return trainNeed;
    }
    @Override
    public void deleteTrainNeed(List<String> deleteIds){
    	TrainNeed trainNeed = null;
    	for(String deleteId:deleteIds){
    		trainNeed = trainNeedDao.get(deleteId);
    		String planId=trainNeed.getTrainPlan().getId();
    		TrainPlan trainPlan= trainPlanManager.get(planId);
    		trainPlan.setState("3");
    		trainPlanManager.save(trainPlan);
    		trainNeedDao.remove(deleteId);
    	}
    }
}