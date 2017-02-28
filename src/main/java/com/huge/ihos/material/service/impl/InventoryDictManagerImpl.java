package com.huge.ihos.material.service.impl;

import java.util.List;

import com.huge.ihos.material.dao.InventoryDictDao;
import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.ihos.material.service.InventoryDictManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("inventoryDictManager")
public class InventoryDictManagerImpl extends GenericManagerImpl<InventoryDict, String> implements InventoryDictManager {
    private InventoryDictDao inventoryDictDao;

    @Autowired
    public InventoryDictManagerImpl(InventoryDictDao inventoryDictDao) {
        super(inventoryDictDao);
        this.inventoryDictDao = inventoryDictDao;
    }
    
    public JQueryPager getInventoryDictCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return inventoryDictDao.getInventoryDictCriteria(paginatedList,filters);
	}

	@Override
	public InventoryDict save(InventoryDict object) {
		object.setCnCode(this.inventoryDictDao.getPyCodes(object.getInvName()));
		return super.save(object);
	}
    
	public void batchEditUpdate(String[] ids, InventoryDict sample) {
		this.inventoryDictDao.batchEditUpdate(ids, sample);
	}
    

	@Override
	public List<InventoryDict> getInventoryDictByInventoryType(
			InventoryType inventoryType) {
		return inventoryDictDao.getInventoryDictByInventoryType(inventoryType);
	}
    
    
}