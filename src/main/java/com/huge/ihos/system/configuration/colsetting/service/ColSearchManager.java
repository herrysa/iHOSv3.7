package com.huge.ihos.system.configuration.colsetting.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.system.configuration.colsetting.model.ColSearch;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ColSearchManager extends GenericManager<ColSearch, String> {
     public JQueryPager getColSearchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public List<ColSearch> getByEntityName(String entityName);
     
     public List<ColSearch> getByTemplName(String templName,String entityName,String userId);
     
     public void delByTemplName(String templName,String entityName,String userId);
     
     public List<HashMap<String,String>> getAllTempl(String entityName,String userId);
}