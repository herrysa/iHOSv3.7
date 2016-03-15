package com.huge.ihos.system.configuration.bdinfo.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.bdinfo.model.FieldInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the FieldInfo table.
 */
public interface FieldInfoDao extends GenericDao<FieldInfo, String> {
	public JQueryPager getFieldInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}