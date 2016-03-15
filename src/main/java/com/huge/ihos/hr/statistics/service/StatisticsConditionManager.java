package com.huge.ihos.hr.statistics.service;


import java.util.List;
import com.huge.ihos.hr.statistics.model.StatisticsCondition;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StatisticsConditionManager extends GenericManager<StatisticsCondition, String> {
     public JQueryPager getStatisticsConditionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void deleteStatisticsConditionAndTarget(String[] ids);
}