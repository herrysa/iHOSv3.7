package com.huge.ihos.kq.kqUpData.service;


import java.util.List;

import com.huge.ihos.kq.kqUpData.model.KqUpItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqUpItemManager extends GenericManager<KqUpItem, String> {
     public JQueryPager getKqUpItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 
      * @param kqUpItem
      * @param entityIsNew
      * @param oldName
      * @return
      */
     public KqUpItem saveKqUpItem(KqUpItem kqUpItem,Boolean entityIsNew,String oldName,String oper) throws Exception;
     /**
      * 获取下一个itemcode
      * @return
      */
     public String getNextItemCode(String kqTypeId);
     /**
      * 默认考勤的天数合计字段
      * @return
      */
     public String getDefaultKqCode(String itemName,String kqTypeId);
}