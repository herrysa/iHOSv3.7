package com.huge.ihos.material.deptplan.dao;


import java.util.List;

import com.huge.ihos.material.deptplan.model.DeptMRSummary;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the DeptMRSummary table.
 */
public interface DeptMRSummaryDao extends GenericDao<DeptMRSummary, String> {
	public JQueryPager getDeptMRSummaryCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}