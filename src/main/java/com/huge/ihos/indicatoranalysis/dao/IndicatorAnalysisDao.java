package com.huge.ihos.indicatoranalysis.dao;


import java.util.List;

import com.huge.ihos.indicatoranalysis.model.IndicatorAnalysis;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the IndicatorAnalysis table.
 */
public interface IndicatorAnalysisDao extends GenericDao<IndicatorAnalysis, String> {
	public JQueryPager getIndicatorAnalysisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}