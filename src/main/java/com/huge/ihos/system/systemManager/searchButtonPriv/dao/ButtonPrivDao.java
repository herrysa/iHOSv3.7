package com.huge.ihos.system.systemManager.searchButtonPriv.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SearchButtonPriv table.
 */
public interface ButtonPrivDao extends GenericDao<ButtonPriv, String> {
	public JQueryPager getButtonPrivCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ButtonPriv> findByRole(String roleId);

	public ButtonPriv findByRoleAndSearch(String roleId, String searchId);
}