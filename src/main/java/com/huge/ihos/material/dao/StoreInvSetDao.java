package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.material.model.StoreInvSet;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the StoreInvSet table.
 */
public interface StoreInvSetDao extends GenericDao<StoreInvSet, String> {
	public JQueryPager getStoreInvSetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<StoreInvSet> getStoreInvSetsByColumn(String columnName,String value);
}