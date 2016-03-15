package com.huge.ihos.hr.trainRecord.service;


import java.util.List;

import com.huge.ihos.hr.trainRecord.model.TrainRecordDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainRecordDetailManager extends GenericManager<TrainRecordDetail, String> {
     public JQueryPager getTrainRecordDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void saveTrainRecordDetailGridDate(String gridAllDatas,String recordId);
     public void writeRecord(String recordId);
}