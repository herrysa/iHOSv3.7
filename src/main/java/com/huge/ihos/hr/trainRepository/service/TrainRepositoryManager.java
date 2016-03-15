package com.huge.ihos.hr.trainRepository.service;


import java.util.List;
import com.huge.ihos.hr.trainRepository.model.TrainRepository;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainRepositoryManager extends GenericManager<TrainRepository, String> {
     public JQueryPager getTrainRepositoryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}