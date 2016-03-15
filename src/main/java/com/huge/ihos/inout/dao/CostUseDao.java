package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.CostUse;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the CostUse table.
 */
public interface CostUseDao
    extends GenericDao<CostUse, String> {

    public JQueryPager getCostUseCriteria( final JQueryPager paginatedList );
}