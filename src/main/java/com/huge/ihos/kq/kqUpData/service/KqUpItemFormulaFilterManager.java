package com.huge.ihos.kq.kqUpData.service;


import java.util.List;
import com.huge.ihos.kq.kqUpData.model.KqUpItemFormulaFilter;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface KqUpItemFormulaFilterManager extends GenericManager<KqUpItemFormulaFilter, String> {
     public JQueryPager getKqUpItemFormulaFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}