package com.huge.ihos.system.configuration.businessprocess.dao;


import java.util.List;

import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessProcess table.
 */
public interface BusinessProcessDao extends GenericDao<BusinessProcess, String> {
	public JQueryPager getBusinessProcessCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}