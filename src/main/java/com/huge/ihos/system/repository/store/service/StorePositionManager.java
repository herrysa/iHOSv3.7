package com.huge.ihos.system.repository.store.service;

import java.util.List;

import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StorePositionManager extends
		GenericManager<StorePosition, String> {
	public JQueryPager getStorePositionCriteria(
			final JQueryPager paginatedList, List<PropertyFilter> filters);

	/**
	 * 查找当前仓库下的货位列表
	 * 
	 * @param store
	 * @return
	 */
	public List<Store> getStoresByStorePosition(Store store);
}