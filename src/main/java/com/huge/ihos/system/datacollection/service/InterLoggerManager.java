package com.huge.ihos.system.datacollection.service;

import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.service.GenericManager;
import com.huge.webapp.pagers.JQueryPager;

public interface InterLoggerManager
    extends GenericManager<InterLogger, Long> {
    public JQueryPager getInterLoggerCriteria( final JQueryPager paginatedList, String interLogId );

    public JQueryPager getInterLoggerCriteria( final JQueryPager paginatedList, String interLogId, String stepName );

    public String newTaskInterId();

    public void deleteByTaskInterId( String tid );

    public void deleteByPeriodCode( String periodCode );
}