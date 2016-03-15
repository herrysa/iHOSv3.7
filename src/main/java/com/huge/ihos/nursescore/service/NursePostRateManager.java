package com.huge.ihos.nursescore.service;


import java.util.List;
import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface NursePostRateManager extends GenericManager<NursePostRate, String> {
     public JQueryPager getNursePostRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}