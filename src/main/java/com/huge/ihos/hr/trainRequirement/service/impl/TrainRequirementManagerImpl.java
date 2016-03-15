package com.huge.ihos.hr.trainRequirement.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.hr.trainRequirement.dao.TrainRequirementDao;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirement;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirementAnalysis;
import com.huge.ihos.hr.trainRequirement.service.TrainRequirementManager;
import com.huge.ihos.system.configuration.serialNumber.model.BillNumberConstants;
import com.huge.ihos.system.configuration.serialNumber.service.BillNumberManager;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("trainRequirementManager")
public class TrainRequirementManagerImpl extends GenericManagerImpl<TrainRequirement, String> implements TrainRequirementManager {
    private TrainRequirementDao trainRequirementDao;
    private BillNumberManager billNumberManager;

    @Autowired
    public TrainRequirementManagerImpl(TrainRequirementDao trainRequirementDao) {
        super(trainRequirementDao);
        this.trainRequirementDao = trainRequirementDao;
    }
    @Autowired
    public void setBillNumberManager(BillNumberManager billNumberManager) {
		this.billNumberManager = billNumberManager;
	}
    public JQueryPager getTrainRequirementCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return trainRequirementDao.getTrainRequirementCriteria(paginatedList,filters);
	}
    @Override
    public void auditTrainRequirement(List<String> checkIds,Person person,Date date,String periodMonth){
    	TrainRequirement trainRequirement = null;
		for(String checkId:checkIds){
			trainRequirement = trainRequirementDao.get(checkId);
			trainRequirement.setState("2");
			trainRequirement.setChecker(person);
			trainRequirement.setCheckDate(date);
			trainRequirement.setPeriodMonth(periodMonth);
			trainRequirementDao.save(trainRequirement);
		}
    }
    @Override
	public void antiTrainRequirement(List<String> cancelCheckIds) {
    	TrainRequirement trainRequirement = null;
		for(String cancelCheckId:cancelCheckIds){
			trainRequirement = trainRequirementDao.get(cancelCheckId);
			trainRequirement.setState("1");
			trainRequirement.setChecker(null);
			trainRequirement.setCheckDate(null);
			trainRequirement.setPeriodMonth(null);
			trainRequirementDao.save(trainRequirement);		
		}
	}
    @Override
    public List<TrainRequirementAnalysis> requirementAnalysis(String checkDateFrom,String checkDateTo){
    	List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("GED_checkDate", checkDateFrom));
		filters.add(new PropertyFilter("LED_checkDate", checkDateTo));
		filters.add(new PropertyFilter("EQS_state","2"));
		List<TrainRequirement> trainRequirements=new ArrayList<TrainRequirement>();
		trainRequirements=trainRequirementDao.getByFilters(filters);
		/*
		 * 按照培训项目统计
		 */
		List<TrainRequirementAnalysis> analysisList=new ArrayList<TrainRequirementAnalysis>();
		List<String> indexList = new ArrayList<String>();
		if(trainRequirements!=null&&trainRequirements.size()>0){
			for(TrainRequirement trainRequirement:trainRequirements){
				if(indexList.contains(trainRequirement.getTrainCategory().getId())){
					int index=indexList.indexOf(trainRequirement.getTrainCategory().getId());
					TrainRequirementAnalysis analysis=new TrainRequirementAnalysis();
					analysis=analysisList.get(index);
					Boolean contain=false;
					for(String deptName:analysis.getDeptName().split(",")){
						if(trainRequirement.getHrDept().getName().equals(deptName)){
							contain=true;
							break;
						}
					}
					if(contain){
						List<String> personIdlist = new ArrayList<String>();  
						String personIds= analysis.getPersonIds()+","+trainRequirement.getPersonIds();
						String personNames=analysis.getPersonNames()+","+trainRequirement.getPersonNames();
						String[] personId=personIds.split(",");
						String[] personName=personNames.split(",");
						String personIdsNew="";
						String personNamesNew="";
						 for (int i=0; i<personId.length; i++) {  
							  if(!personIdlist.contains(personId[i])) {  
								  personIdlist.add(personId[i]);  
								  personIdsNew=personIdsNew+personId[i]+",";
								  personNamesNew=personNamesNew+personName[i]+",";
							  }  
						 }
						 analysis.setPersonNames(personNamesNew.substring(0,personNamesNew.length()-1));
						 analysis.setPersonIds(personIdsNew.substring(0,personIdsNew.length()-1));
						 analysis.setPeopleNumber(personIdlist.size());

					}else{
						analysis.setPersonNames(analysis.getPersonNames()+","+trainRequirement.getPersonNames());
						analysis.setDeptName(analysis.getDeptName()+","+trainRequirement.getHrDept().getName());
						analysis.setPersonIds(analysis.getPersonIds()+","+trainRequirement.getPersonIds());
						analysis.setPeopleNumber(analysis.getPeopleNumber()+trainRequirement.getPeopleNumber());
					}
					analysisList.set(index, analysis);
				}else{
					indexList.add(trainRequirement.getTrainCategory().getId());
					TrainRequirementAnalysis analysis=new TrainRequirementAnalysis();
					analysis.setDeptName(trainRequirement.getHrDept().getName());
					analysis.setTrainCategoryId(trainRequirement.getTrainCategory().getId());
					analysis.setTrainCategoryName(trainRequirement.getTrainCategory().getName());
					analysis.setPersonIds(trainRequirement.getPersonIds());
					analysis.setPeopleNumber(trainRequirement.getPeopleNumber());
					analysis.setPersonNames(trainRequirement.getPersonNames());
					analysisList.add(analysis);
				}
			}
		}
    	return analysisList;
    }
    @Override
    public TrainRequirement saveTrainRequirement(TrainRequirement trainRequirement,Boolean isEntityIsNew){
    	String personNames = trainRequirement.getPersonNames();
		if("".equals(personNames.trim())){
			trainRequirement.setPeopleNumber(0);
		}else{
			trainRequirement.setPeopleNumber(personNames.split(",").length);
		}
		if(isEntityIsNew){
			trainRequirement.setCode(billNumberManager.createNextBillNumberForHRWithYM(BillNumberConstants.HR_TRAIN_NEED,trainRequirement.getYearMonth()));
		}
		trainRequirement=trainRequirementDao.save(trainRequirement);
		return trainRequirement;
    }
	
}