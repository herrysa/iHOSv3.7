package com.huge.ihos.system.systemManager.period.dao;


import java.util.Date;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.period.model.PeriodMonth;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PeriodMonth table.
 */
public interface PeriodMonthDao extends GenericDao<PeriodMonth, String> {
	public JQueryPager getPeriodMonthCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public Boolean removeByPeriodId(String periodId);

	public List<PeriodMonth> getMonthByPlanAndYear(String planId, String year);
	
	public PeriodMonth getPeriodMonth(String planId, Date optDate);

	public List<PeriodMonth> getMonthListBetweenPeriods(String beginPeriod,
			String endPeriod, String planId);

	public List<PeriodMonth> getLessCurrentPeriod(String currentPeriod);
	
	/*public List<PeriodMonth> getPeriodsBetween(String beginPeriod,String endPeriod);*/
	
	public PeriodMonth getPeriodByCode(String periodCode);
}