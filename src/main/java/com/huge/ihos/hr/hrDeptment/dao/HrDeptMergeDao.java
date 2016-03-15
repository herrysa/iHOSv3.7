package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDeptMerge;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDeptMerge table.
 */
public interface HrDeptMergeDao extends GenericDao<HrDeptMerge, String> {
	public JQueryPager getHrDeptMergeCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}