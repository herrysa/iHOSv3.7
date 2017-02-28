package com.huge.ihos.system.configuration.businessprocess.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.configuration.businessprocess.dao.BusinessProcessDao;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcess;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("businessProcessManager")
public class BusinessProcessManagerImpl extends GenericManagerImpl<BusinessProcess, String> implements BusinessProcessManager {
    private BusinessProcessDao businessProcessDao;

    @Autowired
    public BusinessProcessManagerImpl(BusinessProcessDao businessProcessDao) {
        super(businessProcessDao);
        this.businessProcessDao = businessProcessDao;
    }
    
    public JQueryPager getBusinessProcessCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessProcessDao.getBusinessProcessCriteria(paginatedList,filters);
	}
}