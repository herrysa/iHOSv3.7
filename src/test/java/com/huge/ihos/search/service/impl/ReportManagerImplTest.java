package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.ReportDao;
import com.huge.ihos.system.reportManager.search.model.Report;
import com.huge.ihos.system.reportManager.search.service.impl.ReportManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class ReportManagerImplTest
    extends BaseManagerMockTestCase {
    private ReportManagerImpl manager = null;

    private ReportDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( ReportDao.class );
        manager = new ReportManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetReport() {
        log.debug( "testing get..." );

        final Long reportId = 7L;
        final Report report = new Report();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( reportId ) ) );
                will( returnValue( report ) );
            }
        } );

        Report result = manager.get( reportId );
        assertSame( report, result );
    }

    @Test
    public void testGetReports() {
        log.debug( "testing getAll..." );

        final List reports = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( reports ) );
            }
        } );

        List result = manager.getAll();
        assertSame( reports, result );
    }

    @Test
    public void testSaveReport() {
        log.debug( "testing save..." );

        final Report report = new Report();
        // enter all required fields
        report.setGroupName( "LwCdVpMrDzRjKoEpAcUgFyKeMyCkMn" );
        report.setReportName( "WkTxSiQbUhOkPjKkKnEnQmIuYvGdEbRvTtRsZkOhUxYiXmRgKr" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( report ) ) );
            }
        } );

        manager.save( report );
    }

    @Test
    public void testRemoveReport() {
        log.debug( "testing remove..." );

        final Long reportId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( reportId ) ) );
            }
        } );

        manager.remove( reportId );
    }
}