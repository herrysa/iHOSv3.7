package com.huge.ihos.system.repository.store.dao;


import java.util.List;

import com.huge.dao.BaseTreeDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Store table.
 */
public interface StoreDao extends BaseTreeDao<Store, String> {
	public JQueryPager getStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	String getPyCodes( String str );
	public List<Store> getStoreTreeByColumn(String column,String value,String type);
	public List<Store> getAllDescendant(String orgCode,String nodeId,boolean isPos);
	public List<Store> getStoresBySearch(String orgCode,String storeCode, String storeName,
			String storeType, String isPos,String disabled);
}