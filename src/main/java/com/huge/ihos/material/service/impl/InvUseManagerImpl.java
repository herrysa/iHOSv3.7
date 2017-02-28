package com.huge.ihos.material.service.impl;

import java.util.List;

import com.huge.ihos.material.dao.InvUseDao;
import com.huge.ihos.material.model.InvUse;
import com.huge.ihos.material.service.InvUseManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invUseManager")
public class InvUseManagerImpl extends GenericManagerImpl<InvUse, String> implements InvUseManager {
    private InvUseDao invUseDao;

    @Autowired
    public InvUseManagerImpl(InvUseDao invUseDao) {
        super(invUseDao);
        this.invUseDao = invUseDao;
    }
    
    public JQueryPager getInvUseCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invUseDao.getInvUseCriteria(paginatedList,filters);
	}

	@Override
	public List<InvUse> getAllByCO(String copycode, String orgcode) {
		return this.invUseDao.getAllByCO(copycode, orgcode);
	}
}