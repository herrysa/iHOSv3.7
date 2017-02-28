package com.huge.ihos.system.configuration.procType.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.configuration.procType.model.ProcType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the ProcType table.
 */
public interface ProcTypeDao extends GenericDao<ProcType, String> {
	public JQueryPager getProcTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public ProcType getProcTypeByCode(String code);
}