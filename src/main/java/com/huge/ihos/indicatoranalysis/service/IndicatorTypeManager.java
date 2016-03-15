package com.huge.ihos.indicatoranalysis.service;


import java.util.List;

import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface IndicatorTypeManager extends GenericManager<IndicatorType, String> {
     public JQueryPager getIndicatorTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}