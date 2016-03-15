package com.huge.ihos.pz.businesstype.service;


import java.util.List;

import com.huge.ihos.pz.businesstype.model.BusinessTypeJ;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessTypeJManager extends GenericManager<BusinessTypeJ, String> {
     public JQueryPager getBusinessTypeJCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

     public BusinessTypeJ saveBusinessTypeJ(BusinessTypeJ businessTypeJ) throws Exception;
     
     public List<BusinessTypeJ> getAllByBusinessId(String businessId);
     
     public void removeBusinesstypeJ(BusinessTypeJ businessTypeJ);
}