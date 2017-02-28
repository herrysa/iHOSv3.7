package com.huge.ihos.material.dao;


import java.util.List;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the InventoryDict table.
 */
public interface InventoryDictDao extends GenericDao<InventoryDict, String> {
	public JQueryPager getInventoryDictCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public void batchEditUpdate(String[] ids, InventoryDict sample);

	public List<InventoryDict> getInventoryDictByInventoryType(InventoryType inventoryType);
}