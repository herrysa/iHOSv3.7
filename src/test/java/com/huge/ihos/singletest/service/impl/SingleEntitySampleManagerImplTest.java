package com.huge.ihos.singletest.service.impl;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.singletest.dao.SingleEntitySampleDao;
import com.huge.ihos.singletest.model.SingleEntitySample;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SingleEntitySampleManagerImplTest
    extends BaseManagerMockTestCase {
    private SingleEntitySampleManagerImpl manager = null;

    private SingleEntitySampleDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SingleEntitySampleDao.class );
        manager = new SingleEntitySampleManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSingleEntitySample() {
        log.debug( "testing get..." );

        final Long pkid = 7L;
        final SingleEntitySample singleEntitySample = new SingleEntitySample();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( pkid ) ) );
                will( returnValue( singleEntitySample ) );
            }
        } );

        SingleEntitySample result = manager.get( pkid );
        assertSame( singleEntitySample, result );
    }

    @Test
    public void testGetSingleEntitySamples() {
        log.debug( "testing getAll..." );

        final List singleEntitySamples = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( singleEntitySamples ) );
            }
        } );

        List result = manager.getAll();
        assertSame( singleEntitySamples, result );
    }

    @Test
    public void testSaveSingleEntitySample() {
        log.debug( "testing save..." );

        final SingleEntitySample singleEntitySample = new SingleEntitySample();
        // enter all required fields
        singleEntitySample.setBigDecimalField( new BigDecimal( 0 ) );
        singleEntitySample.setBooleanField( Boolean.FALSE );
        singleEntitySample.setDateField( new java.util.Date() );
        singleEntitySample.setDoubleField( new Double( 1.3895031771126848E308 ) );
        singleEntitySample.setFloatField( new Float( 8.653256E37 ) );
        singleEntitySample.setIntField( 646450041 );
        singleEntitySample.setStringField( "OlJxYtHhUzBsBwUzSeJlDtFzTcUlKoKeWpXlBmYhEoLsEhEuNl" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( singleEntitySample ) ) );
            }
        } );

        manager.save( singleEntitySample );
    }

    @Test
    public void testRemoveSingleEntitySample() {
        log.debug( "testing remove..." );

        final Long pkid = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( pkid ) ) );
            }
        } );

        manager.remove( pkid );
    }
}