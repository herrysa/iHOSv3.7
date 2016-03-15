package com.huge.ihos.gz.incomeTaxRate.dao;


import java.util.List;

import com.huge.ihos.gz.incomeTaxRate.model.IncomeTaxRate;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the IncomeTaxRate table.
 */
public interface IncomeTaxRateDao extends GenericDao<IncomeTaxRate, String> {
	public JQueryPager getIncomeTaxRateCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}