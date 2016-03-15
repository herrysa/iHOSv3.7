package com.huge.ihos.strategy.service;


import java.util.List;
import com.huge.ihos.strategy.model.Strategy;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StrategyManager extends GenericManager<Strategy, Integer> {
     public JQueryPager getStrategyCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}