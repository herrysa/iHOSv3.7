package com.huge.ihos.material.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.StoreInvSetDao;
import com.huge.ihos.material.model.StoreInvSet;
import com.huge.ihos.material.service.StoreInvSetManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("storeInvSetManager")
public class StoreInvSetManagerImpl extends GenericManagerImpl<StoreInvSet, String> implements StoreInvSetManager {
    private StoreInvSetDao storeInvSetDao;

    @Autowired
    public StoreInvSetManagerImpl(StoreInvSetDao storeInvSetDao) {
        super(storeInvSetDao);
        this.storeInvSetDao = storeInvSetDao;
    }
    
    public JQueryPager getStoreInvSetCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return storeInvSetDao.getStoreInvSetCriteria(paginatedList,filters);
	}

	@Override
	public List<StoreInvSet> getStoreInvSetsByColumn(String columnName,
			String value) {
		return storeInvSetDao.getStoreInvSetsByColumn(columnName, value);
	}

	@Override
	public boolean isMatch(String storeId, String invId) {
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_store.id",storeId));
		filters.add(new PropertyFilter("EQS_inventoryDict.invId",invId));
		List<StoreInvSet> storeInvSets = this.getByFilters(filters);
		if(storeInvSets!=null && storeInvSets.size()==1){
			return true;
		}
		return false;
	}
}