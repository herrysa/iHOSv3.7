package com.huge.ihos.inout.service;


import java.util.List;
import com.huge.ihos.inout.model.SpecialSource;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface SpecialSourceManager extends GenericManager<SpecialSource, Long> {
     public JQueryPager getSpecialSourceCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public String getCBStatus(String checkPeriod);
     public String getItemType(String itemId);
     public List changeSpecialItemList(String itemTypeName);
}