package com.huge.ihos.system.configuration.businessprocess.service;


import java.util.List;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessProcessStepManager extends GenericManager<BusinessProcessStep, String> {
     public JQueryPager getBusinessProcessStepCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}