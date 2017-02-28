package com.huge.ihos.system.configuration.proj.service;


import java.util.List;

import com.huge.ihos.system.configuration.proj.model.Proj;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ProjManager extends GenericManager<Proj, String> {
     public JQueryPager getProjCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}