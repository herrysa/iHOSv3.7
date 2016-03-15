package com.huge.ihos.nursescore.service;


import java.util.List;
import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface NurseShiftRateManager extends GenericManager<NurseShiftRate, String> {
     public JQueryPager getNurseShiftRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}