package com.huge.ihos.hr.statistics.dao;


import java.util.List;

import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the StatisticsCondition table.
 */
public interface StatisticsConditionDao extends GenericDao<StatisticsCondition, String> {
	public JQueryPager getStatisticsConditionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}