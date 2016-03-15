package com.huge.ihos.hr.trainSite.service;


import java.util.List;
import com.huge.ihos.hr.trainSite.model.TrainSite;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface TrainSiteManager extends GenericManager<TrainSite, String> {
     public JQueryPager getTrainSiteCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}