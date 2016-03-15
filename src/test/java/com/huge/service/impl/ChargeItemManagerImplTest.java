package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.ChargeItemDao;
import com.huge.ihos.inout.model.ChargeItem;
import com.huge.ihos.inout.service.impl.ChargeItemManagerImpl;

public class ChargeItemManagerImplTest
    extends BaseManagerMockTestCase {
    private ChargeItemManagerImpl manager = null;

    private ChargeItemDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( ChargeItemDao.class );
        manager = new ChargeItemManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetChargeItem() {
        log.debug( "testing get..." );

        final String chargeItemId = "XX";
        final ChargeItem chargeItem = new ChargeItem();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( chargeItemId ) ) );
                will( returnValue( chargeItem ) );
            }
        } );

        ChargeItem result = manager.get( chargeItemId );
        assertSame( chargeItem, result );
    }

    @Test
    public void testGetChargeItems() {
        log.debug( "testing getAll..." );

        final List chargeItems = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( chargeItems ) );
            }
        } );

        List result = manager.getAll();
        assertSame( chargeItems, result );
    }

    /*    @Test
     public void testSaveChargeItem() {
     log.debug("testing save...");

     final ChargeItem chargeItem = new ChargeItem();
     // enter all required fields
     chargeItem.setChargeItemId("PkHgVtReUpYnCtEmXaAo");
     chargeItem.setDisabled(Boolean.FALSE);
    
     // set expected behavior on dao
     context.checking(new Expectations() {{
     one(dao).save(with(same(chargeItem)));
     }});

     manager.save(chargeItem);
     }*/

    @Test
    public void testRemoveChargeItem() {
        log.debug( "testing remove..." );

        final String chargeItemId = "XX";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( chargeItemId ) ) );
            }
        } );

        manager.remove( chargeItemId );
    }
}