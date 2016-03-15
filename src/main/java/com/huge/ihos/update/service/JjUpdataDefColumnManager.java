package com.huge.ihos.update.service;


import java.util.List;
import com.huge.ihos.update.model.JjUpdataDefColumn;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface JjUpdataDefColumnManager extends GenericManager<JjUpdataDefColumn, Long> {
     public JQueryPager getJjUpdataDefColumnCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<JjUpdataDefColumn> getEnabledByOrder();
}