package com.huge.ihos.formula.service;


import java.util.List;
import com.huge.ihos.formula.model.UpItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface UpItemManager extends GenericManager<UpItem, Long> {
     public JQueryPager getUpItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public List<UpItem> getUpItemsByDept(String deptId,String upItemClass);
     
     public boolean validataUpCost(Long upItemId);
}