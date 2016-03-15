package com.huge.ihos.pz.businesstype.service.impl;

import java.util.List;

import com.huge.ihos.pz.businesstype.dao.BusinessTypeJDao;
import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.ihos.pz.businesstype.service.BusinessTypeJManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("businessTypeJManager")
public class BusinessTypeJManagerImpl extends GenericManagerImpl<BusinessTypeJ, String> implements BusinessTypeJManager {
    private BusinessTypeJDao businessTypeJDao;

    @Autowired
    public BusinessTypeJManagerImpl(BusinessTypeJDao businessTypeJDao) {
        super(businessTypeJDao);
        this.businessTypeJDao = businessTypeJDao;
    }
    
    public JQueryPager getBusinessTypeJCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessTypeJDao.getBusinessTypeJCriteria(paginatedList,filters);
	}
    
    @Override
    public BusinessTypeJ saveBusinessTypeJ(BusinessTypeJ businessTypeJ) throws Exception {
    	return this.businessTypeJDao.saveBusinessTypeJ(businessTypeJ);
    }
    
    @Override
    public List<BusinessTypeJ> getAllByBusinessId(String businessId) {
    	return this.businessTypeJDao.getAllByBusinessId(businessId);
    }
    
    @Override
    public void removeBusinesstypeJ(BusinessTypeJ businessTypeJ) {
    	this.businessTypeJDao.deleteBusinessTypeJ(businessTypeJ);
    }
}