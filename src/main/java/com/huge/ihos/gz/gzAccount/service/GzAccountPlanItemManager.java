package com.huge.ihos.gz.gzAccount.service;


import java.util.List;

import com.huge.ihos.gz.gzAccount.model.GzAccountPlanItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzAccountPlanItemManager extends GenericManager<GzAccountPlanItem, String> {
     public JQueryPager getGzAccountPlanItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 保存GzAccountPlanItem
      * @param gridAllDatas
      * @param planId
      */
     public void saveGzAccountPlanItem(String gridAllDatas,String planId);
}