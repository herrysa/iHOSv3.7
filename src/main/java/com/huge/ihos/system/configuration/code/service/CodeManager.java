package com.huge.ihos.system.configuration.code.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.system.configuration.code.model.Code;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface CodeManager extends GenericManager<Code, String> {
     public JQueryPager getCodeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List isHaveData(Code code);

	public String getCodeRule(Class clazz, HashMap<String, String> environment);
}