package com.huge.ihos.gz.gzItem.dao;


import java.util.List;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the GzItem table.
 */
public interface GzItemDao extends GenericDao<GzItem, String> {
	public JQueryPager getGzItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public int getMaxSn(String gzTypeId);
}