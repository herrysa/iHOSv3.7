package com.huge.ihos.bm.bugetSubj.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.bm.bugetSubj.model.BugetAcctMap;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BugetAcctMap table.
 */
public interface BugetAcctMapDao extends GenericDao<BugetAcctMap, String> {
	public JQueryPager getBugetAcctMapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}