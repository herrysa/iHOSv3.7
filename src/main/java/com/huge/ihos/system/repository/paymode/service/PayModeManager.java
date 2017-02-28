package com.huge.ihos.system.repository.paymode.service;


import java.util.List;

import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PayModeManager extends GenericManager<PayMode, String> {
     public JQueryPager getPayModeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}