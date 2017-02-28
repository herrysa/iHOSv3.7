package com.huge.ihos.material.deptplan.service;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;

public interface DeptNeedPlanDetailManager extends GenericManager<DeptNeedPlanDetail, String> {
     public JQueryPager getDeptNeedPlanDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
     public Double getLastAmount(String periodMonth,String storeId,String deptId,String invId);
     public Double getsumAmount(String periodMonth,String storeId,String deptId,String invId);
}