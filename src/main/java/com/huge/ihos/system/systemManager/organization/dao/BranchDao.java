package com.huge.ihos.system.systemManager.organization.dao;


import java.util.List;

import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the Branch table.
 */
public interface BranchDao extends GenericDao<Branch, String> {
	public JQueryPager getBranchCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<Branch> getAllAvailable();
	
	public List<Branch> getAllAvailable(String branchPriv);
	
	public List<Branch> getByOrgCode(String orgCode);
}