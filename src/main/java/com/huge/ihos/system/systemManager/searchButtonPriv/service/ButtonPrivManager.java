package com.huge.ihos.system.systemManager.searchButtonPriv.service;

import java.util.Collection;
import java.util.List;

import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface ButtonPrivManager extends GenericManager<ButtonPriv, String> {
	public JQueryPager getButtonPrivCriteria(
			final JQueryPager paginatedList, List<PropertyFilter> filters);

	public List<ButtonPriv> findByRole(String roleId);

	public ButtonPriv findByRoleAndSearch(String roleId, String searchId);

	public void addButtonPriv(
			Collection<ButtonPriv> newButtonPrivs,
			List<ButtonPriv> buttonPrivs);
}