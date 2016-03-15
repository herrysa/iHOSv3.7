package com.huge.ihos.inout.service;


import java.util.List;
import com.huge.ihos.inout.model.SpecialItem;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SpecialItemManager extends GenericManager<SpecialItem, String> {
     public JQueryPager getSpecialItemCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
}