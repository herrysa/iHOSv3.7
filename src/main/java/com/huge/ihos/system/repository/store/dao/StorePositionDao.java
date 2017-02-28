package com.huge.ihos.system.repository.store.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the StorePosition table.
 */
public interface StorePositionDao extends GenericDao<StorePosition, String> {
	public JQueryPager getStorePositionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public List<Store> getStoresByStorePosition(Store store);

}