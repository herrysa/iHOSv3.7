package com.huge.ihos.pz.businesstype.service.impl;

import java.util.List;

import com.huge.ihos.pz.businesstype.dao.BusinessTypeDDao;
import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.ihos.pz.businesstype.service.BusinessTypeDManager;
import com.huge.service.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

@Service("businessTypeDManager")
public class BusinessTypeDManagerImpl extends GenericManagerImpl<BusinessTypeD, String> implements BusinessTypeDManager {
	private BusinessTypeDDao businessTypeDDao;

	@Autowired
	public BusinessTypeDManagerImpl(BusinessTypeDDao businessTypeDDao) {
		super(businessTypeDDao);
		this.businessTypeDDao = businessTypeDDao;
	}

	public JQueryPager getBusinessTypeDCriteria(JQueryPager paginatedList, List<PropertyFilter> filters) {
		return businessTypeDDao.getBusinessTypeDCriteria(paginatedList, filters);
	}

	@Override
	public BusinessTypeD saveBusinessTypeD(BusinessTypeD businessTypeD) throws Exception {
		return businessTypeDDao.saveBusinessTypeD(businessTypeD);
	}
	
	@Override
	public List<BusinessTypeD> getAllByBusinessId(String businessId) {
		return this.businessTypeDDao.getAllByBusinessId(businessId);
	}

	@Override
	public void removeBusinessTypeD(BusinessTypeD businessTypeD) {
		this.businessTypeDDao.deleteBusinessTypeD(businessTypeD);
	}
	
	
}