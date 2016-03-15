package com.huge.ihos.nursescore.dao;


import java.util.List;

import com.huge.ihos.nursescore.model.NurseShiftRate;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the NurseShiftRate table.
 */
public interface NurseShiftRateDao extends GenericDao<NurseShiftRate, String> {
	public JQueryPager getNurseShiftRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}