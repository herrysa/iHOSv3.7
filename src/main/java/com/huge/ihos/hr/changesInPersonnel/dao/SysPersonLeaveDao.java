package com.huge.ihos.hr.changesInPersonnel.dao;


import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonLeave;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SysPersonLeave table.
 */
public interface SysPersonLeaveDao extends GenericDao<SysPersonLeave, String> {
	public JQueryPager getSysPersonLeaveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}