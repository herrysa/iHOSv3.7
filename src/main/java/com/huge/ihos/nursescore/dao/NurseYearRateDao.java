package com.huge.ihos.nursescore.dao;


import java.util.List;

import com.huge.ihos.nursescore.model.NurseYearRate;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the NurseYearRate table.
 */
public interface NurseYearRateDao extends GenericDao<NurseYearRate, String> {
	public JQueryPager getNurseYearRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}