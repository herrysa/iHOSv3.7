package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.period.dao.PeriodDao;
import com.huge.ihos.period.model.Period;
import com.huge.ihos.period.service.impl.PeriodManagerImpl;

public class PeriodManagerImplTest
    extends BaseManagerMockTestCase {
    private PeriodManagerImpl manager = null;

    private PeriodDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( PeriodDao.class );
        manager = new PeriodManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetPeriod() {
        log.debug( "testing get..." );

        final Long periodId = 7L;
        final Period period = new Period();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( periodId ) ) );
                will( returnValue( period ) );
            }
        } );

        Period result = manager.get( periodId );
        assertSame( period, result );
    }

    @Test
    public void testGetPeriods() {
        log.debug( "testing getAll..." );

        final List periods = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( periods ) );
            }
        } );

        List result = manager.getAll();
        assertSame( periods, result );
    }

    @Test
    public void testSavePeriod() {
        log.debug( "testing save..." );

        final Period period = new Period();
        // enter all required fields
        period.setCdpFlag( Boolean.FALSE );
        period.setCmonth( "Cz" );
        period.setCpFlag( Boolean.FALSE );

        period.setCyear( "AqMx" );
        period.setEndDate( new java.util.Date() );
        period.setPeriodCode( "WnBfJt" );
        period.setStartDate( new java.util.Date() );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( period ) ) );
            }
        } );

        manager.save( period );
    }

    @Test
    public void testRemovePeriod() {
        log.debug( "testing remove..." );

        final Long periodId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( periodId ) ) );
            }
        } );

        manager.remove( periodId );
    }
}