package com.huge.ihos.system.systemManager.organization.service;


import java.util.List;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BranchManager extends GenericManager<Branch, String> {
     public JQueryPager getBranchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     /**
      * 获取所有可用的院区（包括系统和停用）
      * @return
      */
     public List<Branch> getAllAvailable();
     /**
      * 获取所有可用的院区（包括系统和停用），添加权限过滤
      * @param branchPriv 权限
      * @return
      */
     public List<Branch> getAllAvailable(String branchPriv);
}