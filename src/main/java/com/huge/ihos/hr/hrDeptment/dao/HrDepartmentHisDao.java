package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentHis;
import com.huge.ihos.hr.hrDeptment.model.HrDeptSnapPk;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDepartmentHis table.
 */
public interface HrDepartmentHisDao extends GenericDao<HrDepartmentHis, HrDeptSnapPk> {
	public JQueryPager getHrDepartmentHisCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}