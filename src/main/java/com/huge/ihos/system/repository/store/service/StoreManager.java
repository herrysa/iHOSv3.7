package com.huge.ihos.system.repository.store.service;


import java.util.List;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.service.BaseTreeManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StoreManager extends BaseTreeManager<Store, String> {
     public JQueryPager getStoreCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<Store> getStoreTreeByColumn(String column,String value,String type);
     public List<Store> getAllDescendant(String orgCode,String nodeId,boolean isPos);
     public List<Store> getStoresBySearch(String orgCode,String storeCode, String storeName,
			String storeType, String isPos,String disabled);
}