package com.huge.ihos.system.systemManager.searchButtonPriv.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SearchButtonPrivUser table.
 */
public interface ButtonPrivUserDao extends GenericDao<ButtonPrivUser, String> {
	public JQueryPager getButtonPrivUserCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<ButtonPrivUser> findByUser(String userId);
}