package com.huge.ihos.material.service;

import java.util.List;

import com.huge.ihos.material.model.StoreInvSet;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface StoreInvSetManager extends GenericManager<StoreInvSet, String> {
     public JQueryPager getStoreInvSetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<StoreInvSet> getStoreInvSetsByColumn(String columnName,String value);
     /**
      * 检测材料与仓库是否有对应关系
      * @param storeId 仓库Id
      * @param invId 材料Id
      * @return true：该仓库含有该材料；false：该仓库不含有该材料
      */
     public boolean isMatch(String storeId,String invId);
}