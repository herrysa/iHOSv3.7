package com.huge.ihos.system.systemManager.period.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.period.model.PeriodPlan;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PeriodPlan table.
 */
public interface PeriodPlanDao extends GenericDao<PeriodPlan, String> {
	public JQueryPager getPeriodPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public PeriodPlan getDefaultPeriodPlan();

}