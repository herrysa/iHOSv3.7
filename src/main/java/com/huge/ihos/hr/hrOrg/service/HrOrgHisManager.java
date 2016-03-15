package com.huge.ihos.hr.hrOrg.service;


import java.util.List;

import com.huge.ihos.hr.hrOrg.model.HrOrgHis;
import com.huge.ihos.hr.hrOrg.model.HrOrgPk;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrOrgHisManager extends GenericManager<HrOrgHis, HrOrgPk> {
     public JQueryPager getHrOrgHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}