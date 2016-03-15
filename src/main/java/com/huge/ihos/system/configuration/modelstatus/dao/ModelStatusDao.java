package com.huge.ihos.system.configuration.modelstatus.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.modelstatus.model.ModelStatus;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ModelStatus table.
 */
public interface ModelStatusDao extends GenericDao<ModelStatus, String> {
	public JQueryPager getModelStatusCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public String getUsingPeriod(String modelId, String periodType);

	public String getClosedPeriod(String modelId, String periodType);

}