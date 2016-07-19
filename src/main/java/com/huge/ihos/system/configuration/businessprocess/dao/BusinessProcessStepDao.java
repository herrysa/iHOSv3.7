package com.huge.ihos.system.configuration.businessprocess.dao;


import java.util.List;

import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessProcessStep table.
 */
public interface BusinessProcessStepDao extends GenericDao<BusinessProcessStep, String> {
	public JQueryPager getBusinessProcessStepCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}