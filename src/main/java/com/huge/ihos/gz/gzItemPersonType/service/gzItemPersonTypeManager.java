package com.huge.ihos.gz.gzItemPersonType.service;


import java.util.List;

import com.huge.ihos.gz.gzItemPersonType.model.GzItemPersonType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface gzItemPersonTypeManager extends GenericManager<GzItemPersonType, String> {
     public JQueryPager getgzItemPersonTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}