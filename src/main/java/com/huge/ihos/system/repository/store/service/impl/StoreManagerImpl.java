package com.huge.ihos.system.repository.store.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.repository.store.dao.StoreDao;
import com.huge.ihos.system.repository.store.model.Store;
import com.huge.ihos.system.repository.store.service.StoreManager;
import com.huge.service.impl.BaseTreeManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("storeManager")
public class StoreManagerImpl extends BaseTreeManagerImpl<Store, String> implements StoreManager {
    private StoreDao storeDao;

    @Autowired
    public StoreManagerImpl(StoreDao storeDao) {
        super(storeDao);
        this.storeDao = storeDao;
    }
    
    public JQueryPager getStoreCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return storeDao.getStoreCriteria(paginatedList,filters);
	}

	@Override
	public List<Store> getStoreTreeByColumn(String column,String value,String type) {
		return storeDao.getStoreTreeByColumn(column,value,type);
	}

	@Override
	public List<Store> getAllDescendant(String orgCode, String nodeId,boolean isPos) {
		return storeDao.getAllDescendant(orgCode,nodeId,isPos);
	}

	@Override
	public Store save(Store object) {
		object.setCnCode(storeDao.getPyCodes(object.getStoreName()));
		return super.insertNode(object);
	}

	@Override
	public List<Store> getStoresBySearch(String orgCode,String storeCode, String storeName,
			String storeType, String isPos,String disabled) {
		return storeDao.getStoresBySearch(orgCode,storeCode, storeName, storeType, isPos,disabled);
	}

}