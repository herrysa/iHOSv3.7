package com.huge.ihos.hr.statistics.dao;


import java.util.List;

import com.huge.ihos.hr.statistics.model.StatisticsDetail;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the StatisticsItem table.
 */
public interface StatisticsItemDao extends GenericDao<StatisticsItem, String> {
	public JQueryPager getStatisticsItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public StatisticsDetail getStatisticsDetail(String statisticsCode);
}