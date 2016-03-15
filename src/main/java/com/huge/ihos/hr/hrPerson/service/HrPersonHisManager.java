package com.huge.ihos.hr.hrPerson.service;


import java.util.List;

import com.huge.ihos.hr.hrPerson.model.HrPersonHis;
import com.huge.ihos.hr.hrPerson.model.HrPersonPK;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrPersonHisManager extends GenericManager<HrPersonHis, HrPersonPK> {
     public JQueryPager getHrPersonHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}