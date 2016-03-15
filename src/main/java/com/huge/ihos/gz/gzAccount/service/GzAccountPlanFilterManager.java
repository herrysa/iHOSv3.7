package com.huge.ihos.gz.gzAccount.service;


import java.util.List;
import com.huge.ihos.gz.gzAccount.model.GzAccountPlanFilter;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzAccountPlanFilterManager extends GenericManager<GzAccountPlanFilter, String> {
     public JQueryPager getGzAccountPlanFliterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}