package com.huge.ihos.system.repository.paymode.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.repository.paymode.model.PayCondition;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PayCondition table.
 */
public interface PayConditionDao extends GenericDao<PayCondition, String> {
	public JQueryPager getPayConditionCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}