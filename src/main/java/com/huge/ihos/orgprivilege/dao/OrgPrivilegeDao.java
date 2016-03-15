package com.huge.ihos.orgprivilege.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.orgprivilege.model.OrgPrivilege;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the OrgPrivilege table.
 */
public interface OrgPrivilegeDao extends GenericDao<OrgPrivilege, String> {
	public JQueryPager getOrgPrivilegeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	
	/**
	 * 根据人员ID获取部门IDS
	 * @param personId
	 * @return
	 */
	public String[] getByOperatorId(String personId);
}