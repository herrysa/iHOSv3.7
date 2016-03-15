package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.AllotItemDetailDao;
import com.huge.ihos.formula.model.AllotItemDetail;
import com.huge.service.impl.BaseManagerMockTestCase;

public class AllotItemDetailManagerImplTest
    extends BaseManagerMockTestCase {
    private AllotItemDetailManagerImpl manager = null;

    private AllotItemDetailDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( AllotItemDetailDao.class );
        manager = new AllotItemDetailManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetAllotItemDetail() {
        log.debug( "testing get..." );

        final Long allotItemDetailId = 7L;
        final AllotItemDetail allotItemDetail = new AllotItemDetail();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( allotItemDetailId ) ) );
                will( returnValue( allotItemDetail ) );
            }
        } );

        AllotItemDetail result = manager.get( allotItemDetailId );
        assertSame( allotItemDetail, result );
    }

    @Test
    public void testGetAllotItemDetails() {
        log.debug( "testing getAll..." );

        final List allotItemDetails = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( allotItemDetails ) );
            }
        } );

        List result = manager.getAll();
        assertSame( allotItemDetails, result );
    }

    @Test
    public void testSaveAllotItemDetail() {
        log.debug( "testing save..." );

        final AllotItemDetail allotItemDetail = new AllotItemDetail();
        // enter all required fields
        allotItemDetail.setCostRatio( new BigDecimal( 0 ) );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( allotItemDetail ) ) );
            }
        } );

        manager.save( allotItemDetail );
    }

    @Test
    public void testRemoveAllotItemDetail() {
        log.debug( "testing remove..." );

        final Long allotItemDetailId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( allotItemDetailId ) ) );
            }
        } );

        manager.remove( allotItemDetailId );
    }
}