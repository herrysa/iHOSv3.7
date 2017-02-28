package com.huge.ihos.bm.bugetSubj.service;


import java.util.List;

import com.huge.ihos.bm.bugetSubj.model.BugetSubj;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BugetSubjManager extends GenericManager<BugetSubj, String> {
     public JQueryPager getBugetSubjCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}	