package com.huge.ihos.nursescore.dao;


import java.util.List;

import com.huge.ihos.nursescore.model.MonthNurse;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the MonthNurse table.
 */
public interface MonthNurseDao extends GenericDao<MonthNurse, Long> {
	public JQueryPager getMonthNurseCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

	public List<MonthNurse> getByCheckPeriodAndDept(String checkPeriod,String deptId);
}