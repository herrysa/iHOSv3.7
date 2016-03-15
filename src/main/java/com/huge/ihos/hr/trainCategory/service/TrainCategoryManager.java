package com.huge.ihos.hr.trainCategory.service;


import java.util.List;
import com.huge.ihos.hr.trainCategory.model.TrainCategory;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainCategoryManager extends GenericManager<TrainCategory, String> {
     public JQueryPager getTrainCategoryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}