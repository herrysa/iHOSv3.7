package com.huge.ihos.system.systemManager.period.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.period.model.MoudlePeriod;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the MoudlePeriod table.
 */
public interface MoudlePeriodDao extends GenericDao<MoudlePeriod, String> {
	public JQueryPager getMoudlePeriodCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public void removeBySubId(String periodSubject_periodId);

	public List<MoudlePeriod> getMoudlePeriod(String planId,String periodSubId,String moudleId);
}