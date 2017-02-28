package com.huge.ihos.material.deptplan.dao;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptMRSummaryDetail;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptMRSummaryDetail table.
 */
public interface DeptMRSummaryDetailDao extends GenericDao<DeptMRSummaryDetail, String> {
	public JQueryPager getDeptMRSummaryDetailCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}