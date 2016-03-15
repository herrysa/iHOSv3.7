package com.huge.ihos.system.systemManager.organization.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.Org;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Org table.
 */
public interface OrgDao extends GenericDao<Org, String> {
	public JQueryPager getOrgCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Org> getRightOrg(List dataprivi);

	public List<Org> getOrgsByParent(String parentId);

	public Org getOrgById(String orgId);
	 /**
	  * 同步单位后停用未同步到的单位
	  * @param snapCode
	  */
	 public void disableOrgAfterSync(String snapCode);
}