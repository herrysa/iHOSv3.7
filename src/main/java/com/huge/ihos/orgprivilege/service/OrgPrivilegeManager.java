package com.huge.ihos.orgprivilege.service;


import java.util.List;
import com.huge.ihos.orgprivilege.model.OrgPrivilege;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface OrgPrivilegeManager extends GenericManager<OrgPrivilege, String> {
     public JQueryPager getOrgPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public String getOrgPrivilegeToStr(String personId);
}