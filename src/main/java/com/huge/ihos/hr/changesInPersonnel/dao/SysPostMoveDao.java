package com.huge.ihos.hr.changesInPersonnel.dao;


import java.util.List;

import com.huge.ihos.hr.changesInPersonnel.model.SysPostMove;
import com.huge.dao.GenericDao;
import com.huge.webapp.pagers.JQueryPager;
import com.huge.webapp.util.PropertyFilter;
/**
 * An interface that provides a data management interface to the SysPostMove table.
 */
public interface SysPostMoveDao extends GenericDao<SysPostMove, String> {
	public JQueryPager getSysPostMoveCriteria(final JQueryPager paginatedList,List<PropertyFilter> filters);

}