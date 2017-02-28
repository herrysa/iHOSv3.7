package com.huge.ihos.material.businessType.service;


import java.util.List;
import com.huge.ihos.material.businessType.model.MmBusinessType;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface MmBusinessTypeManager extends GenericManager<MmBusinessType, String> {
     public JQueryPager getBusinessTypeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public List<MmBusinessType> getBusTypeByIo(String io);
}