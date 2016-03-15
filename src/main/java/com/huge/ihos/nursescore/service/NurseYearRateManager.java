package com.huge.ihos.nursescore.service;


import java.util.List;
import com.huge.ihos.nursescore.model.NurseYearRate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface NurseYearRateManager extends GenericManager<NurseYearRate, String> {
     public JQueryPager getNurseYearRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}