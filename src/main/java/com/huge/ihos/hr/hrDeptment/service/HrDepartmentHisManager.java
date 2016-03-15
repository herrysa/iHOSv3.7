package com.huge.ihos.hr.hrDeptment.service;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDeptSnapPk;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface HrDepartmentHisManager extends GenericManager<HrDepartmentHis, HrDeptSnapPk> {
     public JQueryPager getHrDepartmentHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
}