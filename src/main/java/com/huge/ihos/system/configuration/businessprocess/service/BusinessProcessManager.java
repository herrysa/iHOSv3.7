package com.huge.ihos.system.configuration.businessprocess.service;


import java.util.List;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessProcessManager extends GenericManager<BusinessProcess, String> {
     public JQueryPager getBusinessProcessCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}