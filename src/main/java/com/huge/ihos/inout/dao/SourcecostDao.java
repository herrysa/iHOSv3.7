package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.Sourcecost;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Sourcecost table.
 */
public interface SourcecostDao
    extends GenericDao<Sourcecost, Long> {

    public JQueryPager getSourcecostCriteria( final JQueryPager paginatedList );

    public String getAmountSum( String currentPeriod );
}