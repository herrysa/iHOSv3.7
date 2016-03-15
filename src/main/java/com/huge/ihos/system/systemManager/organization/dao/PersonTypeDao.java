package com.huge.ihos.system.systemManager.organization.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.PersonType;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrPersonType table.
 */
public interface PersonTypeDao extends GenericDao<PersonType, String> {
	public JQueryPager getPersonTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}