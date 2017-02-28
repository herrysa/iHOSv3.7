package com.huge.ihos.material.service.impl;

import java.util.List;

import com.huge.ihos.material.dao.InvDetailDao;
import com.huge.ihos.material.model.InvDetail;
import com.huge.ihos.material.model.InvMain;
import com.huge.ihos.material.service.InvDetailManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("invDetailManager")
public class InvDetailManagerImpl extends GenericManagerImpl<InvDetail, String> implements InvDetailManager {
    private InvDetailDao invDetailDao;

    @Autowired
    public InvDetailManagerImpl(InvDetailDao invDetailDao) {
        super(invDetailDao);
        this.invDetailDao = invDetailDao;
    }
    
    public JQueryPager getInvDetailCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invDetailDao.getInvDetailCriteria(paginatedList,filters);
	}

	@Override
	public List<InvDetail> getInvDetailsInSameInvMain(InvMain invMain) {
		return this.invDetailDao.getInvDetailsInSameInvMain(invMain);
	}
    
    
}