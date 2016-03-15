package com.huge.ihos.inout.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the Sourcepayin table.
 */
public interface SourcepayinDao
    extends GenericDao<Sourcepayin, Long> {

    public JQueryPager getSourcepayinCriteria( final JQueryPager paginatedList );

    public String getAmountSum( String currentPeriod );
}