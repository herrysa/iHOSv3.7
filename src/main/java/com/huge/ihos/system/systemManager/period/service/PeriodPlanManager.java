package com.huge.ihos.system.systemManager.period.service;


import java.util.List;

import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PeriodPlanManager extends GenericManager<PeriodPlan, String> {
     public JQueryPager getPeriodPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 取得默认方案
      */
     public PeriodPlan getDefaultPeriodPlan();
}