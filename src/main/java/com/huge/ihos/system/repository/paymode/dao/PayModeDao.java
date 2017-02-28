package com.huge.ihos.system.repository.paymode.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.repository.paymode.model.PayMode;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PayMode table.
 */
public interface PayModeDao extends GenericDao<PayMode, String> {
	public JQueryPager getPayModeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}