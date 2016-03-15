package com.huge.ihos.singletest.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the SingleEntitySample table.
 */
public interface SingleEntitySampleDao
    extends GenericDao<SingleEntitySample, Long> {

    public JQueryPager getSingleEntitySampleCriteria( final JQueryPager paginatedList );
}