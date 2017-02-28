package com.huge.ihos.bm.bugetSubj.service;


import java.util.List;

import com.huge.ihos.bm.bugetSubj.model.BugetAcctMap;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BugetAcctMapManager extends GenericManager<BugetAcctMap, String> {
     public JQueryPager getBugetAcctMapCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}