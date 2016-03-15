package com.huge.ihos.pz.businesstype.service;


import java.util.List;

import com.huge.ihos.pz.businesstype.model.BusinessTypeD;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessTypeDManager extends GenericManager<BusinessTypeD, String> {
     public JQueryPager getBusinessTypeDCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public BusinessTypeD saveBusinessTypeD(BusinessTypeD businessTypeD) throws Exception;
     
     public List<BusinessTypeD> getAllByBusinessId(String businessId);
     
     public void removeBusinessTypeD(BusinessTypeD businessTypeD);
}