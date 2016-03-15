package com.huge.ihos.system.systemManager.dataprivilege.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the PrivilegeClass table.
 */
public interface PrivilegeClassDao extends GenericDao<PrivilegeClass, String> {
	public JQueryPager getPrivilegeClassCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<PrivilegeClass> findEnabledClass();
	
	public List<PrivilegeClass> getAllByCode(String classCode);
}