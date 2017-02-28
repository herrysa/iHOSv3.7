package com.huge.ihos.accounting.kjyear.service;


import java.util.HashMap;
import java.util.List;

import com.huge.ihos.accounting.kjyear.model.KjYear;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KjYearManager extends GenericManager<KjYear, String> {
     public JQueryPager getKjYearCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public KjYear getKjyearByDate(String orgCode,String optDate);

	public KjYear getKjYear(HashMap<String, String> environment);
}