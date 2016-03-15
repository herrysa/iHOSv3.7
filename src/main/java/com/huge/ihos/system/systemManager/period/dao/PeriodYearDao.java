package com.huge.ihos.system.systemManager.period.dao;


import java.util.Date;
import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.period.model.PeriodYear;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PeriodSubject table.
 */
public interface PeriodYearDao extends GenericDao<PeriodYear, String> {
	public JQueryPager getPeriodYearCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public PeriodYear getLastYearByPlan(String planId);

	public PeriodYear getPeriodYearByPlanAndYear(String planId, String year);

	public PeriodYear getPeriodYearByPlanAndDate(String planId, Date date);

}