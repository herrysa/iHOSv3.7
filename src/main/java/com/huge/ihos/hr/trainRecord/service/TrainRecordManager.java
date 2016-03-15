package com.huge.ihos.hr.trainRecord.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.hr.trainRecord.model.TrainRecord;
import com.huge.ihos.system.systemManager.organization.model.Person;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainRecordManager extends GenericManager<TrainRecord, String> {
     public JQueryPager getTrainRecordCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void auditTrainRecord(List<String> checkIds,Person person,Date date);
     public void antiTrainRecord(List<String> cancelCheckIds);
     public void doneTrainRecord(List<String> doneIds,Person person,Date date);
     public TrainRecord saveTrainRecord(TrainRecord trainRecord,Boolean isEntityIsNew,String gridAllDatas);
}