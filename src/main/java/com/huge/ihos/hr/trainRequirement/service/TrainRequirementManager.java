package com.huge.ihos.hr.trainRequirement.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.trainRequirement.model.TrainRequirement;
import com.huge.ihos.hr.trainRequirement.model.TrainRequirementAnalysis;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainRequirementManager extends GenericManager<TrainRequirement, String> {
     public JQueryPager getTrainRequirementCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditTrainRequirement(List<String> checkIds,Person person,Date date,String periodMonth);
     public void antiTrainRequirement(List<String> cancelCheckIds);
     public List<TrainRequirementAnalysis> requirementAnalysis(String checkDateFrom,String checkDateTo);
     public TrainRequirement saveTrainRequirement(TrainRequirement trainRequirement,Boolean isEntityIsNew);
}