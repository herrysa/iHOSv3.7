package com.huge.ihos.system.configuration.serialNumber.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.serialNumber.model.SerialNumberSet;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SerialNumberSet table.
 */
public interface SerialNumberSetDao extends GenericDao<SerialNumberSet, String> {
	public JQueryPager getSerialNumberSetCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}