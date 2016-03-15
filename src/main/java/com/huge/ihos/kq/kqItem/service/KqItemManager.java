package com.huge.ihos.kq.kqItem.service;


import java.util.List;

import com.huge.ihos.kq.kqItem.model.KqItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqItemManager extends GenericManager<KqItem, String> {
     public JQueryPager getKqItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 
      * @return
      */
     public KqItem getDefaultKqItem();
     /**
      * 
      * @return
      */
     public List<KqItem> getLeafKqItems();
}