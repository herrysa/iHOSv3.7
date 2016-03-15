package com.huge.ihos.system.systemManager.organization.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.systemManager.organization.model.KhDeptType;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DeptType table.
 */
public interface KhDeptTypeDao
    extends GenericDao<KhDeptType, String> {

    public JQueryPager getKhDeptTypeCriteria( final JQueryPager paginatedList );
}