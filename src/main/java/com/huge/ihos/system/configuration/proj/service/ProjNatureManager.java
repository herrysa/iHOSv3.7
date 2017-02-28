package com.huge.ihos.system.configuration.proj.service;


import java.util.List;

import com.huge.ihos.system.configuration.proj.model.ProjNature;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProjNatureManager extends GenericManager<ProjNature, String> {
     public JQueryPager getProjNatureCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ProjNature> getAllEnabled();
}