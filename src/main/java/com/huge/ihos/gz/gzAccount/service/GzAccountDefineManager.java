package com.huge.ihos.gz.gzAccount.service;


import java.util.List;
import com.huge.ihos.gz.gzAccount.model.GzAccountDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzAccountDefineManager extends GenericManager<GzAccountDefine, String> {
     public JQueryPager getGzAccountDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}