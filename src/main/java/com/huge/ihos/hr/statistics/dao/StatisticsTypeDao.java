package com.huge.ihos.hr.statistics.dao;


import java.util.List;

import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the StatisticsType table.
 */
public interface StatisticsTypeDao extends GenericDao<StatisticsType, String> {
	public JQueryPager getStatisticsTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}