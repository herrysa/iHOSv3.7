package com.huge.ihos.interlog.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.dao.InterLoggerDao;
import com.huge.ihos.system.datacollection.model.InterLogger;
import com.huge.ihos.system.datacollection.service.impl.InterLoggerManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class InterLoggerManagerImplTest
    extends BaseManagerMockTestCase {
    private InterLoggerManagerImpl manager = null;

    private InterLoggerDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( InterLoggerDao.class );
        manager = new InterLoggerManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetInterLogger() {
        log.debug( "testing get..." );

        final Long logId = 7L;
        final InterLogger interLogger = new InterLogger();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( logId ) ) );
                will( returnValue( interLogger ) );
            }
        } );

        InterLogger result = manager.get( logId );
        assertSame( interLogger, result );
    }

    @Test
    public void testGetInterLoggers() {
        log.debug( "testing getAll..." );

        final List interLoggers = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( interLoggers ) );
            }
        } );

        List result = manager.getAll();
        assertSame( interLoggers, result );
    }

    @Test
    public void testSaveInterLogger() {
        log.debug( "testing save..." );

        final InterLogger interLogger = new InterLogger();
        // enter all required fields
        interLogger.setLogDateTime( new java.util.Date() );
        interLogger.setLogFrom( "NwPtHwNeQsFlUlMvAjTbUyGxWfWzHhGzCfUdErEqDyAuFfAlCw" );
        interLogger.setLogMsg( "NyIwQoEpSbEhItQcIpQhExOqBnHoDaSzXkHvNaLyNvJuIcFgSn" );
        interLogger.setTaskInterId( "NsHxVsIqUjKtDrXtBoGgRgHjFtXqSoUbNpIdRaRrGmSwQrPiBr" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( interLogger ) ) );
            }
        } );

        manager.save( interLogger );
    }

    @Test
    public void testRemoveInterLogger() {
        log.debug( "testing remove..." );

        final Long logId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( logId ) ) );
            }
        } );

        manager.remove( logId );
    }
}