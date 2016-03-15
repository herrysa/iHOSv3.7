package com.huge.ihos.gz.gzItem.service;


import java.util.List;

import com.huge.ihos.gz.gzItem.model.GzItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzItemManager extends GenericManager<GzItem, String> {
     public JQueryPager getGzItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	 /**
	  * 取当前类别下的下一个序号
	  * @param gzTypeId
	  * @return
	  */
	 public int getMaxSn(String gzTypeId);
	 /**
	  * 获得下一个itemCode
	  * @return
	  */
	 public String getNextItemCode();
	 /**
	  * 获得计税项
	  * @param gzTypeId
	  */
	 public GzItem getIsTaxGzItem(String gzTypeId);
	 /**
	  * 获得个人所得税项
	  * @param gzTypeId
	  * @return
	  */
	 public GzItem getGzItemByName(String gzTypeId,String itemName);
	 /**
	  * 保存工资项并且添加字段
	  * @param gzItem
	  * @param entityIsNew
	  * @return
	  */
	 public GzItem saveGzItem(GzItem gzItem,Boolean entityIsNew,String oldName);
}