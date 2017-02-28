package com.huge.ihos.material.deptplan.dao;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptNeedPlan;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptNeedPlan table.
 */
public interface DeptNeedPlanDao extends GenericDao<DeptNeedPlan, String> {
	public JQueryPager getDeptNeedPlanCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}