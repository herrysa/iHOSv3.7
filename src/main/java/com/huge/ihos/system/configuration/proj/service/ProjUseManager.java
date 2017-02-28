package com.huge.ihos.system.configuration.proj.service;


import java.util.List;

import com.huge.ihos.system.configuration.proj.model.ProjUse;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProjUseManager extends GenericManager<ProjUse, String> {
     public JQueryPager getProjUseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ProjUse> getAllEnabled();
}