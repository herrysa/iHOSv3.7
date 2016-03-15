package com.huge.ihos.inout.dao;


import java.util.List;

import com.huge.ihos.inout.model.SpecialItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SpecialItem table.
 */
public interface SpecialItemDao extends GenericDao<SpecialItem, String> {
	public JQueryPager getSpecialItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}