package com.huge.ihos.system.systemManager.menu.service;


import java.util.List;

import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface MenuButtonManager extends GenericManager<MenuButton, String> {
     public JQueryPager getMenuButtonCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public String[] getDistinctMenuIds(Object obj);
     public MenuButton getByMenuIdAndButtonId(String menuId,String buttonId);
     public List<MenuButton> getMenuButtonsInRight(String menuId,String userId);
	 public Long createNextButtonSn(String menuId);
}