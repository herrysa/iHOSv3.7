package com.huge.ihos.system.configuration.bdinfo.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.bdinfo.model.BdInfo;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BdInfo table.
 */
public interface BdInfoDao extends GenericDao<BdInfo, String> {
	public JQueryPager getBdInfoCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public BdInfo getBdInfoByClazz(Class clazz);
	
	public BdInfo findByTableName(String tableName);

}