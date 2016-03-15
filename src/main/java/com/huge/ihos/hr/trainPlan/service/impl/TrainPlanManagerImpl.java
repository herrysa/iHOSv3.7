package com.huge.ihos.hr.trainPlan.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainPlan.dao.TrainPlanDao;
import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.hr.trainPlan.service.TrainPlanManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainPlanManager")
public class TrainPlanManagerImpl extends GenericManagerImpl<TrainPlan, String> implements TrainPlanManager {
    private TrainPlanDao trainPlanDao;
    private BillNumberManager billNumberManager;

    @Autowired
    public TrainPlanManagerImpl(TrainPlanDao trainPlanDao) {
        super(trainPlanDao);
        this.trainPlanDao = trainPlanDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getTrainPlanCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainPlanDao.getTrainPlanCriteria(paginatedList,filters);
	}
    @Override
    public void auditTrainPlan(List<String> checkIds,Person person,Date date){
    	TrainPlan trainPlan = null;
		for(String checkId:checkIds){
			trainPlan = trainPlanDao.get(checkId);
			trainPlan.setState("2");
			trainPlan.setChecker(person);
			trainPlan.setCheckDate(date);
			trainPlanDao.save(trainPlan);
		}
    }
    @Override
	public void antiTrainPlan(List<String> cancelCheckIds) {
    	TrainPlan trainPlan = null;
		for(String cancelCheckId:cancelCheckIds){
			trainPlan = trainPlanDao.get(cancelCheckId);
			trainPlan.setState("1");
			trainPlan.setChecker(null);
			trainPlan.setCheckDate(null);
			trainPlanDao.save(trainPlan);		
		}
	}
    @Override
    public TrainPlan saveTrainPlan(TrainPlan trainPlan,Boolean isEntityIsNew){
    	String personNames = trainPlan.getPersonNames();
		if("".equals(personNames.trim())){
			trainPlan.setPeopleNumber(0);
		}else{
			trainPlan.setPeopleNumber(personNames.split(",").length);
		}
		if(isEntityIsNew){
			trainPlan.setCode(billNumberManager.createNextBillNumberForHRWithYM( BillNumberConstants.HR_TRAIN_PLAN,trainPlan.getYearMonth()));
		}
		trainPlan=trainPlanDao.save(trainPlan);
		return trainPlan;
    }
}