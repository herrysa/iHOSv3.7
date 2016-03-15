package com.huge.ihos.system.systemManager.period.service;


import java.util.Date;
import java.util.List;

import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PeriodMonthManager extends GenericManager<PeriodMonth, String> {
    public JQueryPager getPeriodMonthCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

    public List<PeriodMonth> getMonthByPlanAndYear(String planId, String kjYear);
	
    public PeriodMonth getPeriodMonth(String planId, Date optDate);
	
	public String getMonthsBetweenPeriods(String beginPeriod, String endPeriod, String planId);
	
	public List<PeriodMonth> getMonthListBetweenPeriods(String beginPeriod, String endPeriod, String planId);
	
	public List<PeriodMonth> getLessCurrentPeriod(String currentPeriod);
	
	/*public List<PeriodMonth> getPeriodsBetween(String beginPeriod,String endPeriod);*/
	
	public PeriodMonth getPeriodByCode(String periodCode);
	/**
	 * 得到当前期间的下一个期间，如果当前期间为最后一个期间返回null
	 * @param planId
	 * @param kjYear
	 * @param currentPeriod
	 * @return
	 */
	public String getNextPeriod(String planId, String kjYear,String currentPeriod);

}