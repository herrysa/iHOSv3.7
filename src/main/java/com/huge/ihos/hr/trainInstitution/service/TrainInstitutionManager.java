package com.huge.ihos.hr.trainInstitution.service;


import java.util.List;
import com.huge.ihos.hr.trainInstitution.model.TrainInstitution;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainInstitutionManager extends GenericManager<TrainInstitution, String> {
     public JQueryPager getTrainInstitutionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}