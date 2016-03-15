package com.huge.ihos.system.systemManager.dataprivilege.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DataPrivilege table.
 */
public interface DataPrivilegeDao extends GenericDao<DataPrivilege, String> {
	public JQueryPager getDataPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	public List<DataPrivilege> getDataPrivilegesByRole(String roleId);
	
	public List<DataPrivilege> getDataPrivilegesByRole(String roleId,String classCode);

	public void deleteByRoleIdAndClass(String roleId,PrivilegeClass privilegeClass);
}