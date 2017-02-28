package com.huge.ihos.system.systemManager.busiprocess.service;


import java.util.List;

import com.huge.ihos.system.systemManager.busiprocess.model.BusiProcessLog;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusiProcessLogManager extends GenericManager<BusiProcessLog, Long> {
     public JQueryPager getBusinessProcessLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}