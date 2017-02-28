package com.huge.ihos.material.service;


import java.util.List;

import com.huge.ihos.material.model.InventoryDict;
import com.huge.ihos.material.model.InventoryType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InventoryDictManager extends GenericManager<InventoryDict, String> {
     public JQueryPager getInventoryDictCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void batchEditUpdate(String[] ids, InventoryDict sample);
     public List<InventoryDict> getInventoryDictByInventoryType(InventoryType inventoryType);
}