package com.huge.ihos.hr.statistics.service;


import java.util.List;

import com.huge.ihos.hr.statistics.model.StatisticsDetail;
import com.huge.ihos.hr.statistics.model.StatisticsItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StatisticsItemManager extends GenericManager<StatisticsItem, String> {
     public JQueryPager getStatisticsItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public StatisticsDetail getStatisticsDetail(String statisticsCode);
     public void deleteStatisticsItem(String[] ids);
}