package com.huge.ihos.system.configuration.proj.service;


import java.util.List;

import com.huge.ihos.system.configuration.proj.model.ProjType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProjTypeManager extends GenericManager<ProjType, String> {
     public JQueryPager getProjTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public String pyCode(String string);

	public List<ProjType> getAllEnabled();
}