package com.huge.ihos.strategy.dao;


import java.util.List;

import com.huge.ihos.strategy.model.Strategy;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Strategy table.
 */
public interface StrategyDao extends GenericDao<Strategy, Integer> {
	public JQueryPager getStrategyCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}