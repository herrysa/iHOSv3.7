package com.huge.ihos.kaohe.service;


import java.util.List;
import com.huge.ihos.kaohe.model.InspectTempl;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface InspectTemplManager extends GenericManager<InspectTempl, String> {
     public JQueryPager getInspectTemplCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public InspectTempl deptIsSelected(String deptId,String periodType,String selfTemplId);
}