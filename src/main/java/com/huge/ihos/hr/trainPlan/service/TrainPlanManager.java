package com.huge.ihos.hr.trainPlan.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.trainPlan.model.TrainPlan;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainPlanManager extends GenericManager<TrainPlan, String> {
     public JQueryPager getTrainPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditTrainPlan(List<String> checkIds,Person person,Date date);
     public void antiTrainPlan(List<String> cancelCheckIds);
     public TrainPlan saveTrainPlan(TrainPlan trainPlan,Boolean isEntityIsNew);
}