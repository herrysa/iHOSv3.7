package com.huge.ihos.system.reportManager.report.service;


import java.util.List;
import com.huge.ihos.system.reportManager.report.model.UserReportDefine;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface UserReportDefineManager extends GenericManager<UserReportDefine, String> {
     public JQueryPager getUserReportDefineCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}