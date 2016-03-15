package com.huge.ihos.hr.changesInPersonnel.dao;


import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPersonMove;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the sysPersonMove table.
 */
public interface SysPersonMoveDao extends GenericDao<SysPersonMove, String> {
	public JQueryPager getSysPersonMoveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}