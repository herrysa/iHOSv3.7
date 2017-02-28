package com.huge.ihos.material.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.material.dao.InventoryTypeDao;
import com.huge.ihos.material.model.InventoryType;
import com.huge.ihos.material.service.InventoryTypeManager;
import com.huge.service.impl.BaseTreeManagerImpl;

@Service("inventoryTypeManager")
public class InventoryTypeManagerImpl extends BaseTreeManagerImpl<InventoryType, String> implements InventoryTypeManager {
    private InventoryTypeDao inventoryTypeDao;

    @Autowired
    public InventoryTypeManagerImpl(InventoryTypeDao inventoryTypeDao) {
        super(inventoryTypeDao);
        this.inventoryTypeDao = inventoryTypeDao;
    }

	@Override
	public List<InventoryType> getFullInvTypeTree(String orgCode,
			String copyCode,String type) {
		return inventoryTypeDao.getFullInvTypeTree(orgCode, copyCode, type);
		
	}

	@Override
	public InventoryType save(InventoryType object) {
		object.setCnCode( inventoryTypeDao.getPyCodes( object.getInvTypeName()));
		return super.insertNode(object);
	}
	
	public List<InventoryType> getAllDescendant(String orgCode,String copyCode,String nodeId){
		return inventoryTypeDao.getAllDescendant(orgCode, copyCode, nodeId);
	}
	
	
    
}