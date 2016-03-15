package com.huge.ihos.system.systemManager.organization.service;


import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface OrgManager extends GenericManager<Org, String> {
     public JQueryPager getOrgCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<Org> getRightOrg(Long userId);
     /**
      * 获取直接下级
      * @param removeId
      * @return
      */
	 public List<Org> getOrgsByParent(String removeId);
	 public boolean isNewOrg(String orgId);
	 /**
	  * 获取所有下级单位
	  * @param orgCode
	  * @return
	  */
	 public List<Org> getAllDescendants(String orgCode);
	 /**
	  * 获取所有可用的单位集合(去除停用的和系统的)，根据orgCode升序（ASC）
	  * @return 如果不存在可用的单位则返回null
	  */
	 public List<Org> getAllAvailable();
	 /**
	  * 同步单位后停用未同步到的单位
	  * @param snapCode
	  */
	 public void disableOrgAfterSync(String snapCode);
	 public List<Org> getAllExcXT();
	 
	 public String checkDelete(String removeId);
}