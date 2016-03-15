package com.huge.ihos.system.systemManager.dataprivilege.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the UserDataPrivilege table.
 */
public interface UserDataPrivilegeDao extends GenericDao<UserDataPrivilege, String> {
	public JQueryPager getUserDataPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public void deleteByUserIdAndClass(String userId, PrivilegeClass privilegeClass,String exceptIds);
	
	public List<UserDataPrivilege> findByUserIdAndClass(String userId,String classCode);
	
	public List<UserDataPrivilege> getByUserId(String userId);
}