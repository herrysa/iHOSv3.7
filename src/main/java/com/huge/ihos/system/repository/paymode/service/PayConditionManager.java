package com.huge.ihos.system.repository.paymode.service;


import java.util.List;

import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PayConditionManager extends GenericManager<PayCondition, String> {
     public JQueryPager getPayConditionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}