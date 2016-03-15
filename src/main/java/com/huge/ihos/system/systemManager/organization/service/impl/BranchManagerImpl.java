package com.huge.ihos.system.systemManager.organization.service.impl;

import java.util.List;

import com.huge.ihos.system.systemManager.organization.dao.BranchDao;
import com.huge.ihos.system.systemManager.organization.model.Branch;
import com.huge.ihos.system.systemManager.organization.service.BranchManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("branchManager")
public class BranchManagerImpl extends GenericManagerImpl<Branch, String> implements BranchManager {
    private BranchDao branchDao;

    @Autowired
    public BranchManagerImpl(BranchDao branchDao) {
        super(branchDao);
        this.branchDao = branchDao;
    }
    
    public JQueryPager getBranchCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return branchDao.getBranchCriteria(paginatedList,filters);
	}
    
    @Override
    public List<Branch> getAllAvailable() {
    	return this.branchDao.getAllAvailable();
    }
    @Override
    public List<Branch> getAllAvailable(String branchPriv) {
    	return this.branchDao.getAllAvailable(branchPriv);
    }
}