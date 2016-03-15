package com.huge.ihos.system.datacollection.dao;

import com.huge.dao.GenericDao;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.webapp.pagers.JQueryPager;

/**
 * An interface that provides a data management interface to the InterLogger table.
 */
public interface InterLoggerDao
    extends GenericDao<InterLogger, Long> {

    public JQueryPager getInterLoggerCriteria( final JQueryPager paginatedList, String interLogId );

    public JQueryPager getInterLoggerCriteria( JQueryPager paginatedList, String interLogId, String stepName );

    public void deleteByTaskInterId( String tid );

    public void deleteByPeriodCode( String periodCode );
}