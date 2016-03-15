package com.huge.ihos.system.systemManager.organization.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DeptType table.
 */
public interface DeptTypeDao
    extends GenericDao<DeptType, String> {

    public JQueryPager getDeptTypeCriteria( final JQueryPager paginatedList );
}