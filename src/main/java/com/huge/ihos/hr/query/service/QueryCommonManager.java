package com.huge.ihos.hr.query.service;


import java.util.List;

import com.huge.ihos.hr.query.model.QueryCommon;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface QueryCommonManager extends GenericManager<QueryCommon, String> {
     public JQueryPager getQueryCommonCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public void deleteQueryCommonAndDetail(String[] ids);
}