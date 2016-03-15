package com.huge.ihos.system.systemManager.menu.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the MenuButton table.
 */
public interface MenuButtonDao extends GenericDao<MenuButton, String> {
	public JQueryPager getMenuButtonCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public String[] getDistinctMenuIds(Object obj);

	public MenuButton getByMenuIdAndButtonId(String menuId, String buttonId);
	
	public List<MenuButton> getMenuButtonsInRight(String menuId,String userId);

}