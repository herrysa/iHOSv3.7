package com.huge.ihos.hr.hrDeptment.dao;


import java.util.List;
import java.util.Map;

import com.huge.dao.GenericDao;
import com.huge.ihos.hr.hrDeptment.model.HrDepartmentCurrent;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the HrDepartmentCurrent table.
 */
public interface HrDepartmentCurrentDao extends GenericDao<HrDepartmentCurrent, String> {
	public JQueryPager getHrDepartmentCurrentCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public JQueryPager getHrDepartmentCriteriaForSetPlan(final JQueryPager paginatedList,List<PropertyFilter> filters);
	public Map<String, String> getDeptIdAndSnapCodeMap();
	public List<Integer> getSUM(String string, List<PropertyFilter> filters);
}