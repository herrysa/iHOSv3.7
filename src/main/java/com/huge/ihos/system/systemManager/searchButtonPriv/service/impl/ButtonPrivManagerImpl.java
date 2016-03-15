package com.huge.ihos.system.systemManager.searchButtonPriv.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPriv;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("buttonPrivManager")
public class ButtonPrivManagerImpl extends GenericManagerImpl<ButtonPriv, String> implements ButtonPrivManager {
    private ButtonPrivDao buttonPrivDao;

    @Autowired
    public ButtonPrivManagerImpl(ButtonPrivDao buttonPrivDao) {
        super(buttonPrivDao);
        this.buttonPrivDao = buttonPrivDao;
    }
    
    public JQueryPager getButtonPrivCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return buttonPrivDao.getButtonPrivCriteria(paginatedList,filters);
	}

	@Override
	public List<ButtonPriv> findByRole(String roleId) {
		return buttonPrivDao.findByRole(roleId);
	}

	@Override
	public ButtonPriv findByRoleAndSearch(String roleId, String searchId) {
		return buttonPrivDao.findByRoleAndSearch(roleId,searchId);
	}

	@Override
	public void addButtonPriv(
			Collection<ButtonPriv> newButtonPrivs,List<ButtonPriv> oldButtonPrivs) {
		for(ButtonPriv buttonPriv:oldButtonPrivs){
			buttonPrivDao.remove(buttonPriv.getPrivId());
		}
		for(ButtonPriv buttonPriv:newButtonPrivs){
			buttonPrivDao.save(buttonPriv);
		}
	}
	
	
}