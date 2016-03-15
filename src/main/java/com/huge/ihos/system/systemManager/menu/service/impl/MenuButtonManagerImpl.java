package com.huge.ihos.system.systemManager.menu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.menu.dao.MenuButtonDao;
import com.huge.ihos.system.systemManager.menu.model.MenuButton;
import com.huge.ihos.system.systemManager.menu.service.MenuButtonManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("menuButtonManager")
public class MenuButtonManagerImpl extends GenericManagerImpl<MenuButton, String> implements MenuButtonManager {
    private MenuButtonDao menuButtonDao;

    @Autowired
    public MenuButtonManagerImpl(MenuButtonDao menuButtonDao) {
        super(menuButtonDao);
        this.menuButtonDao = menuButtonDao;
    }
    
    public JQueryPager getMenuButtonCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return menuButtonDao.getMenuButtonCriteria(paginatedList,filters);
	}

	@Override
	public String[] getDistinctMenuIds(Object obj) {
		return menuButtonDao.getDistinctMenuIds(obj);
	}

	@Override
	public MenuButton getByMenuIdAndButtonId(String menuId, String buttonId) {
		return menuButtonDao.getByMenuIdAndButtonId(menuId,buttonId);
	}

	@Override
	public List<MenuButton> getMenuButtonsInRight(String menuId, String userId) {
		return menuButtonDao.getMenuButtonsInRight(menuId,userId);
	}

	@Override
	public Long createNextButtonSn(String menuId) {
		Long initSn = 1L;
		List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
		filters.add(new PropertyFilter("EQS_menuId",menuId));
		filters.add(new PropertyFilter("ODS_id",""));
		List<MenuButton> menuButtons = this.getByFilters(filters);
		if(menuButtons!=null && !menuButtons.isEmpty()){
			/*for(MenuButton menuButton:menuButtons){
				Long sn = menuButton.getOorder();
				if(sn>initSn){
					initSn = sn;
				}
			}
			initSn++;*/
			initSn = new Long(menuButtons.size()+1);
		}
		return initSn;
	}
}