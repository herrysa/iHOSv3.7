package com.huge.ihos.system.configuration.procType.service;


import java.util.List;

import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProcTypeManager extends GenericManager<ProcType, String> {
     public JQueryPager getProcTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public ProcType getProcTypeByCode(String code);
}