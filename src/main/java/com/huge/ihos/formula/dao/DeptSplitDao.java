package com.huge.ihos.formula.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.formula.model.DeptSplit;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the DeptSplit table.
 */
public interface DeptSplitDao
    extends GenericDao<DeptSplit, Long> {

    public JQueryPager getDeptSplitCriteria( final JQueryPager paginatedList );
}