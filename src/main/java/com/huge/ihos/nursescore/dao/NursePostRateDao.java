package com.huge.ihos.nursescore.dao;


import java.util.List;

import com.huge.ihos.nursescore.model.NursePostRate;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the NursePostRate table.
 */
public interface NursePostRateDao extends GenericDao<NursePostRate, String> {
	public JQueryPager getNursePostRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}