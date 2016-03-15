package com.huge.ihos.system.systemManager.dataprivilege.service;


import java.util.List;

import com.huge.ihos.system.systemManager.dataprivilege.model.UserDataPrivilege;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface UserDataPrivilegeManager extends GenericManager<UserDataPrivilege, String> {
     public JQueryPager getUserDataPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public void deleteByUserIdAndClass(String userId,String classCode,String exceptIds);
     
     public List<UserDataPrivilege> findByUserIdAndClass(String userId,String classCode);
}