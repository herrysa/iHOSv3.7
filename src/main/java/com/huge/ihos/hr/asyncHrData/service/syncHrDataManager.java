package com.huge.ihos.hr.asyncHrData.service;


import java.util.List;
import com.huge.ihos.hr.asyncHrData.model.syncHrData;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface syncHrDataManager extends GenericManager<syncHrData, String> {
     public JQueryPager getsyncHrDataCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}