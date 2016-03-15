package com.huge.ihos.hr.trainInformation.service;


import java.util.List;
import com.huge.ihos.hr.trainInformation.model.TrainInformation;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainInformationManager extends GenericManager<TrainInformation, String> {
     public JQueryPager getTrainInformationCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}