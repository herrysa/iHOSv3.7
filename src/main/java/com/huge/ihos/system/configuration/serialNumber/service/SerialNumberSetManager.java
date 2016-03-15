package com.huge.ihos.system.configuration.serialNumber.service;


import java.util.List;

import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SerialNumberSetManager extends GenericManager<SerialNumberSet, String> {
     public JQueryPager getSerialNumberSetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}