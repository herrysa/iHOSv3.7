package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.chart.dao.MccKeyDetailDao;
import com.huge.ihos.system.reportManager.chart.model.MccKeyDetail;
import com.huge.ihos.system.reportManager.chart.service.impl.MccKeyDetailManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class MccKeyDetailManagerImplTest
    extends BaseManagerMockTestCase {
    private MccKeyDetailManagerImpl manager = null;

    private MccKeyDetailDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( MccKeyDetailDao.class );
        manager = new MccKeyDetailManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetMccKeyDetail() {
        log.debug( "testing get..." );

        final String mccKeyDetailId = "7";
        final MccKeyDetail mccKeyDetail = new MccKeyDetail();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( mccKeyDetailId ) ) );
                will( returnValue( mccKeyDetail ) );
            }
        } );

        MccKeyDetail result = manager.get( mccKeyDetailId );
        assertSame( mccKeyDetail, result );
    }

    @Test
    public void testGetMccKeyDetails() {
        log.debug( "testing getAll..." );

        final List mccKeyDetails = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( mccKeyDetails ) );
            }
        } );

        List result = manager.getAll();
        assertSame( mccKeyDetails, result );
    }

    @Test
    public void testSaveMccKeyDetail() {
        log.debug( "testing save..." );

        final MccKeyDetail mccKeyDetail = new MccKeyDetail();
        // enter all required fields

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( mccKeyDetail ) ) );
            }
        } );

        manager.save( mccKeyDetail );
    }

    @Test
    public void testRemoveMccKeyDetail() {
        log.debug( "testing remove..." );

        final String mccKeyDetailId = "-11";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( mccKeyDetailId ) ) );
            }
        } );

        manager.remove( mccKeyDetailId );
    }
}