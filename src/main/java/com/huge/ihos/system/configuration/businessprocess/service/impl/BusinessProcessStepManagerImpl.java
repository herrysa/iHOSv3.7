package com.huge.ihos.system.configuration.businessprocess.service.impl;

import java.util.List;
import com.huge.ihos.system.configuration.businessprocess.dao.BusinessProcessStepDao;
import com.huge.ihos.system.configuration.businessprocess.model.BusinessProcessStep;
import com.huge.ihos.system.configuration.businessprocess.service.BusinessProcessStepManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("businessProcessStepManager")
public class BusinessProcessStepManagerImpl extends GenericManagerImpl<BusinessProcessStep, String> implements BusinessProcessStepManager {
    private BusinessProcessStepDao businessProcessStepDao;

    @Autowired
    public BusinessProcessStepManagerImpl(BusinessProcessStepDao businessProcessStepDao) {
        super(businessProcessStepDao);
        this.businessProcessStepDao = businessProcessStepDao;
    }
    
    public JQueryPager getBusinessProcessStepCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessProcessStepDao.getBusinessProcessStepCriteria(paginatedList,filters);
	}
}