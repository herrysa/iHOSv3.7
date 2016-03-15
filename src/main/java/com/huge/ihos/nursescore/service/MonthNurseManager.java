package com.huge.ihos.nursescore.service;


import java.util.List;
import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface MonthNurseManager extends GenericManager<MonthNurse, Long> {
     public JQueryPager getMonthNurseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     
     public List<MonthNurse> getByCheckPeriodAndDept(String checkPeriod,String deptId);
}