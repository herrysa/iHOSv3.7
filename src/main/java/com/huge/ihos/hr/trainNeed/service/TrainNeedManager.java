package com.huge.ihos.hr.trainNeed.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.trainNeed.model.TrainNeed;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainNeedManager extends GenericManager<TrainNeed, String> {
     public JQueryPager getTrainNeedCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditTrainNeed(List<String> checkIds,Person person,Date date);
     public void antiTrainNeed(List<String> cancelCheckIds);
     public void deleteTrainNeed(List<String> deleteIds);
     public TrainNeed saveTrainNeed(TrainNeed trainNeed,Boolean isEntityIsNew);
}