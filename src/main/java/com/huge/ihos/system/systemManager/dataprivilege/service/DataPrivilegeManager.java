package com.huge.ihos.system.systemManager.dataprivilege.service;


import java.util.List;

import com.huge.ihos.system.systemManager.dataprivilege.model.DataPrivilege;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DataPrivilegeManager extends GenericManager<DataPrivilege, String> {
     public JQueryPager getDataPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<DataPrivilege> findByRoleIdAndClass(String roleId,String classCode);
     
     public void deleteByRoleIdAndClass(String roleId,String classCode);
}