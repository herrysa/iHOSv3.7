package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;

import com.huge.ihos.hr.hrDeptment.model.HrDeptRescind;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDeptRescind table.
 */
public interface HrDeptRescindDao extends GenericDao<HrDeptRescind, String> {
	public JQueryPager getHrDeptRescindCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}