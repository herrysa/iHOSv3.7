package com.huge.ihos.system.systemManager.period.service;


import java.util.List;

import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface MoudlePeriodManager extends GenericManager<MoudlePeriod, String> {
     public JQueryPager getMoudlePeriodCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public void removeBySubId(String periodSubject_periodId);
	
	public List<MoudlePeriod> getMoudlePeriod(String planId,String periodSubId,String moudleId);
}