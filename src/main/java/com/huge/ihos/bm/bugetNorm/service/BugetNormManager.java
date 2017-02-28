package com.huge.ihos.bm.bugetNorm.service;


import java.util.List;
import com.huge.ihos.bm.bugetNorm.model.BugetNorm;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BugetNormManager extends GenericManager<BugetNorm, String> {
     public JQueryPager getBugetNormCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}