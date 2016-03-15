package com.huge.ihos.system.systemManager.period.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PeriodYearManager extends GenericManager<PeriodYear, String> {
     public JQueryPager getPeriodYearCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public PeriodYear getLastYearByPlan(String planId);

	public PeriodYear savePeriodYear(PeriodYear periodyear,String periodSubject_status);
	
	public PeriodYear getPeriodYearByPlanAndYear(String planId, String year);
	
	public PeriodYear getPeriodYearByPlanAndDate(String planId, Date date);
}