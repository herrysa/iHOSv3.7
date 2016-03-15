package com.huge.ihos.system.systemManager.dataprivilege.service;


import java.util.List;

import com.huge.ihos.system.systemManager.dataprivilege.model.PrivilegeClass;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface PrivilegeClassManager extends GenericManager<PrivilegeClass, String> {
     public JQueryPager getPrivilegeClassCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<PrivilegeClass> findEnabledClass();
     
     public List<PrivilegeClass> getAllByCode(String classCode);
}