package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDeptNew;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDeptNew table.
 */
public interface HrDeptNewDao extends GenericDao<HrDeptNew, String> {
	public JQueryPager getHrDeptNewCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}