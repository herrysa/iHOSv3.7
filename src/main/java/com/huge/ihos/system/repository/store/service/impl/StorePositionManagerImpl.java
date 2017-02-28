package com.huge.ihos.system.repository.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.store.dao.StorePositionDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.model.StorePosition;
import com.huge.ihos.system.repository.store.service.StorePositionManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("storePositionManager")
public class StorePositionManagerImpl extends
		GenericManagerImpl<StorePosition, String> implements
		StorePositionManager {
	private StorePositionDao storePositionDao;

	@Autowired
	public StorePositionManagerImpl(StorePositionDao storePositionDao) {
		super(storePositionDao);
		this.storePositionDao = storePositionDao;
	}

	public JQueryPager getStorePositionCriteria(JQueryPager paginatedList,
			List<PropertyFilter> filters) {
		return storePositionDao
				.getStorePositionCriteria(paginatedList, filters);
	}

	@Override
	public List<Store> getStoresByStorePosition(Store store) {
		return storePositionDao.getStoresByStorePosition(store);
	}

}