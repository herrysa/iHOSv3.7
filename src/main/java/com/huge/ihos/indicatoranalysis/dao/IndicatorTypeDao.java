package com.huge.ihos.indicatoranalysis.dao;


import java.util.List;

import com.huge.ihos.indicatoranalysis.model.IndicatorType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the IndicatorType table.
 */
public interface IndicatorTypeDao extends GenericDao<IndicatorType, String> {
	public JQueryPager getIndicatorTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}