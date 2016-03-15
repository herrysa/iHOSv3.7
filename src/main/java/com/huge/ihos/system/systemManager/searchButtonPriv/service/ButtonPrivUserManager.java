package com.huge.ihos.system.systemManager.searchButtonPriv.service;


import java.util.Collection;
import java.util.List;

import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ButtonPrivUserManager extends GenericManager<ButtonPrivUser, String> {
     public JQueryPager getButtonPrivUserCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public List<ButtonPrivUser> findByUser(String userId);

	 public void addButtonPrivUser(
			Collection<ButtonPrivUser> newButtonPrivUsers,
			List<ButtonPrivUser> bButtonPrivUsers);
}