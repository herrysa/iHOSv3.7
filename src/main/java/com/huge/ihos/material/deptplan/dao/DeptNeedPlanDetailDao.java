package com.huge.ihos.material.deptplan.dao;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptNeedPlanDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptNeedPlanDetail table.
 */
public interface DeptNeedPlanDetailDao extends GenericDao<DeptNeedPlanDetail, String> {
	public JQueryPager getDeptNeedPlanDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public Double getLastAmount(String lastPeriodMonth,String storeId,String deptId,String invId);
}