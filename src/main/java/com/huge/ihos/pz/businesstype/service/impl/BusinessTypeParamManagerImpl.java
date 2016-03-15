package com.huge.ihos.pz.businesstype.service.impl;

import java.util.List;
import com.huge.ihos.pz.businesstype.dao.BusinessTypeParamDao;
import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.ihos.pz.businesstype.service.BusinessTypeParamManager;
import com.huge.service.impl.GenericManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;





@Service("businessTypeParamManager")
public class BusinessTypeParamManagerImpl extends GenericManagerImpl<BusinessTypeParam, String> implements BusinessTypeParamManager {
    private BusinessTypeParamDao businessTypeParamDao;

    @Autowired
    public BusinessTypeParamManagerImpl(BusinessTypeParamDao businessTypeParamDao) {
        super(businessTypeParamDao);
        this.businessTypeParamDao = businessTypeParamDao;
    }
    
    public JQueryPager getBusinessTypeParamCriteria(JQueryPager paginatedList,List<PropertyFilter> filters) {
		return businessTypeParamDao.getBusinessTypeParamCriteria(paginatedList,filters);
	}
}