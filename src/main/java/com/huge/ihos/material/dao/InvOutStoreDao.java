package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.ihos.material.model.InvOutStore;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InvOutStore table.
 */
public interface InvOutStoreDao extends GenericDao<InvOutStore, String> {
	public JQueryPager getInvOutStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}