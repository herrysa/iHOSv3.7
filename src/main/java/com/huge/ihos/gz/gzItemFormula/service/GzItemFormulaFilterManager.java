package com.huge.ihos.gz.gzItemFormula.service;


import java.util.List;
import com.huge.ihos.gz.gzItemFormula.model.GzItemFormulaFilter;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface GzItemFormulaFilterManager extends GenericManager<GzItemFormulaFilter, String> {
     public JQueryPager getGzItemFormulaFilterCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}