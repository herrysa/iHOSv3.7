package com.huge.ihos.material.typeno.service.impl;

import java.util.List;

import com.huge.ihos.material.typeno.dao.InvTypeNoDao;
import com.huge.ihos.material.typeno.model.InvTypeNo;
import com.huge.ihos.material.typeno.service.InvTypeNoManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("invTypeNoManager")
public class InvTypeNoManagerImpl extends GenericManagerImpl<InvTypeNo, Long> implements InvTypeNoManager {
    private InvTypeNoDao invTypeNoDao;

    @Autowired
    public InvTypeNoManagerImpl(InvTypeNoDao invTypeNoDao) {
        super(invTypeNoDao);
        this.invTypeNoDao = invTypeNoDao;
    }
    
    public JQueryPager getInvTypeNoCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return invTypeNoDao.getInvTypeNoCriteria(paginatedList,filters);
	}

	@Override
	public String getTypeByNo(String no,String orgCode,String copyCode) {
		// TODO Auto-generated method stub
		return invTypeNoDao.getTypeByNo(no,orgCode,copyCode);
	}
    
    
}