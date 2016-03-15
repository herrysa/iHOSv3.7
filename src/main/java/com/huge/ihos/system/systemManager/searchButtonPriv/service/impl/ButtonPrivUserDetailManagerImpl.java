package com.huge.ihos.system.systemManager.searchButtonPriv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.systemManager.searchButtonPriv.dao.ButtonPrivUserDetailDao;
import com.huge.ihos.system.systemManager.searchButtonPriv.model.ButtonPrivUserDetail;
import com.huge.ihos.system.systemManager.searchButtonPriv.service.ButtonPrivUserDetailManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("buttonPrivUserDetailManager")
public class ButtonPrivUserDetailManagerImpl extends GenericManagerImpl<ButtonPrivUserDetail, String> implements ButtonPrivUserDetailManager {
    private ButtonPrivUserDetailDao buttonPrivUserDetailDao;

    @Autowired
    public ButtonPrivUserDetailManagerImpl(ButtonPrivUserDetailDao buttonPrivUserDetailDao) {
        super(buttonPrivUserDetailDao);
        this.buttonPrivUserDetailDao = buttonPrivUserDetailDao;
    }
    
    public JQueryPager getButtonPrivUserDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return buttonPrivUserDetailDao.getButtonPrivUserDetailCriteria(paginatedList,filters);
	}

	@Override
	public List<ButtonPrivUserDetail> findByUserAndSearch(String userId,
			String searchId) {
		return buttonPrivUserDetailDao.findByUserAndSearch(userId,searchId);
	}
    
    
}