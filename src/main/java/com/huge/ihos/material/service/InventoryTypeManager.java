package com.huge.ihos.material.service;

import java.util.List;

import com.huge.ihos.material.model.InventoryType;
import com.huge.service.BaseTreeManager;

public interface InventoryTypeManager extends BaseTreeManager<InventoryType, String> {
	List<InventoryType> getFullInvTypeTree(String orgCode,String copyCode,String type);
	public List<InventoryType> getAllDescendant(String orgCode,String copyCode,String nodeId);
}