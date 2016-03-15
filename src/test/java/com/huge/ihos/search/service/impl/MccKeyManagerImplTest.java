package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.chart.dao.MccKeyDao;
import com.huge.ihos.system.reportManager.chart.model.MccKey;
import com.huge.ihos.system.reportManager.chart.service.impl.MccKeyManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class MccKeyManagerImplTest
    extends BaseManagerMockTestCase {
    private MccKeyManagerImpl manager = null;

    private MccKeyDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( MccKeyDao.class );
        manager = new MccKeyManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetMccKey() {
        log.debug( "testing get..." );

        final String mccKeyId = "7";
        final MccKey mccKey = new MccKey();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( mccKeyId ) ) );
                will( returnValue( mccKey ) );
            }
        } );

        MccKey result = manager.get( mccKeyId );
        assertSame( mccKey, result );
    }

    @Test
    public void testGetMccKeys() {
        log.debug( "testing getAll..." );

        final List mccKeys = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( mccKeys ) );
            }
        } );

        List result = manager.getAll();
        assertSame( mccKeys, result );
    }

    @Test
    public void testSaveMccKey() {
        log.debug( "testing save..." );

        final MccKey mccKey = new MccKey();
        // enter all required fields

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( mccKey ) ) );
            }
        } );

        manager.save( mccKey );
    }

    @Test
    public void testRemoveMccKey() {
        log.debug( "testing remove..." );

        final String mccKeyId = "7";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( mccKeyId ) ) );
            }
        } );

        manager.remove( mccKeyId );
    }
}