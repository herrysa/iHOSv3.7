package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.dao.BaseTreeDao;
import com.huge.ihos.material.model.InventoryType;
/**
 * An interface that provides a data management interface to the InventoryType table.
 */
public interface InventoryTypeDao extends BaseTreeDao<InventoryType, String> {
	List<InventoryType> getFullInvTypeTree(String orgCode,String copyCode,String type);
	String getPyCodes( String str );
	public List<InventoryType> getAllDescendant(String orgCode,String copyCode,String nodeId);
}