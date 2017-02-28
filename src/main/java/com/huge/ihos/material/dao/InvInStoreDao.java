package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.ihos.material.model.InvInStore;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvInStore table.
 */
public interface InvInStoreDao extends GenericDao<InvInStore, String> {
	public JQueryPager getInvInStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}