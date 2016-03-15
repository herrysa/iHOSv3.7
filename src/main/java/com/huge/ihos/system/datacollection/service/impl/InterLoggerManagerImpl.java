package com.huge.ihos.system.datacollection.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.InterLoggerManager;
import com.huge.service.impl.GenericManagerImpl;
import com.huge.util.UUIDGenerator;
import com.huge.webapp.pagers.JQueryPager;

@Service( "interLoggerManager" )
public class InterLoggerManagerImpl
    extends GenericManagerImpl<InterLogger, Long>
    implements InterLoggerManager {
    InterLoggerDao interLoggerDao;

    @Autowired
    public InterLoggerManagerImpl( InterLoggerDao interLoggerDao ) {
        super( interLoggerDao );
        this.interLoggerDao = interLoggerDao;
    }

    public JQueryPager getInterLoggerCriteria( JQueryPager paginatedList, String interLogId ) {
        return interLoggerDao.getInterLoggerCriteria( paginatedList, interLogId );
    }

    public JQueryPager getInterLoggerCriteria( JQueryPager paginatedList, String interLogId, String stepName ) {
        return interLoggerDao.getInterLoggerCriteria( paginatedList, interLogId, stepName );
    }

    public String newTaskInterId() {
        return UUIDGenerator.getInstance().getNextValue16();
    }

    public void deleteByTaskInterId( String tid ) {
        this.interLoggerDao.deleteByTaskInterId( tid );
    }

    @Override
    public void deleteByPeriodCode( String periodCode ) {
        this.interLoggerDao.deleteByPeriodCode( periodCode );

    }
}