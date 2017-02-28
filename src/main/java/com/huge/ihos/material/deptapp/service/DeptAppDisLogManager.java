package com.huge.ihos.material.deptapp.service;


import java.util.List;
import com.huge.ihos.material.deptapp.model.DeptAppDisLog;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptAppDisLogManager extends GenericManager<DeptAppDisLog, String> {
     public JQueryPager getDeptAppDisLogCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}