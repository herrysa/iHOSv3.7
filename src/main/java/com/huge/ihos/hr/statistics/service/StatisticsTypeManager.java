package com.huge.ihos.hr.statistics.service;


import java.util.List;
import com.huge.ihos.hr.statistics.model.StatisticsType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StatisticsTypeManager extends GenericManager<StatisticsType, String> {
     public JQueryPager getStatisticsTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public StatisticsType saveStatisticsType(StatisticsType statisticsType);
     public void deleteStatisticsType(String[] ids);
}