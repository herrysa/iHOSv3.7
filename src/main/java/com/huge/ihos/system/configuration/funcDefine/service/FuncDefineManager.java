package com.huge.ihos.system.configuration.funcDefine.service;


import java.util.List;

import com.huge.ihos.system.configuration.funcDefine.model.FuncDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface FuncDefineManager extends GenericManager<FuncDefine, String> {
     public JQueryPager getFuncDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}