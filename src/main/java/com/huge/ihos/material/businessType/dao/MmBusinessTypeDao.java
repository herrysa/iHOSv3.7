package com.huge.ihos.material.businessType.dao;


import java.util.List;

import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the BusinessType table.
 */
public interface MmBusinessTypeDao extends GenericDao<MmBusinessType, String> {
	public JQueryPager getBusinessTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<MmBusinessType> getBusTypeByIo(String io);

}