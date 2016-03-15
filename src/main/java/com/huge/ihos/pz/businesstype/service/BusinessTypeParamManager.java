package com.huge.ihos.pz.businesstype.service;


import java.util.List;
import com.huge.ihos.pz.businesstype.model.BusinessTypeParam;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface BusinessTypeParamManager extends GenericManager<BusinessTypeParam, String> {
     public JQueryPager getBusinessTypeParamCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}