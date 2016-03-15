package com.huge.ihos.system.systemManager.searchButtonPriv.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUser;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivUserManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("buttonPrivUserManager")
public class ButtonPrivUserManagerImpl extends GenericManagerImpl<ButtonPrivUser, String> implements ButtonPrivUserManager {
    private ButtonPrivUserDao buttonPrivUserDao;

    @Autowired
    public ButtonPrivUserManagerImpl(ButtonPrivUserDao buttonPrivUserDao) {
        super(buttonPrivUserDao);
        this.buttonPrivUserDao = buttonPrivUserDao;
    }
    
    public JQueryPager getButtonPrivUserCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return buttonPrivUserDao.getButtonPrivUserCriteria(paginatedList,filters);
	}
    
    @Override
	public List<ButtonPrivUser> findByUser(String userId) {
		return buttonPrivUserDao.findByUser(userId);
	}

	@Override
	public void addButtonPrivUser(
			Collection<ButtonPrivUser> newButtonPrivUsers,
			List<ButtonPrivUser> oldButtonPrivUsers) {
		for(ButtonPrivUser searchButtonPrivUser:oldButtonPrivUsers){
			buttonPrivUserDao.remove(searchButtonPrivUser.getPrivId());
		}
		for(ButtonPrivUser searchButtonPrivUser:newButtonPrivUsers){
			buttonPrivUserDao.save(searchButtonPrivUser);
		}
		
	}
    
    
}