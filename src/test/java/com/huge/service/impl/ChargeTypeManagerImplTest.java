package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.ChargeTypeDao;
import com.huge.ihos.inout.model.ChargeType;
import com.huge.ihos.inout.service.impl.ChargeTypeManagerImpl;

public class ChargeTypeManagerImplTest
    extends BaseManagerMockTestCase {
    private ChargeTypeManagerImpl manager = null;

    private ChargeTypeDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( ChargeTypeDao.class );
        manager = new ChargeTypeManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetChargeType() {
        log.debug( "testing get..." );

        final String chargeTypeId = "7L";
        final ChargeType chargeType = new ChargeType();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( chargeTypeId ) ) );
                will( returnValue( chargeType ) );
            }
        } );

        ChargeType result = manager.get( chargeTypeId );
        assertSame( chargeType, result );
    }

    @Test
    public void testGetChargeTypes() {
        log.debug( "testing getAll..." );

        final List chargeTypes = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( chargeTypes ) );
            }
        } );

        List result = manager.getAll();
        assertSame( chargeTypes, result );
    }

    /*   @Test
       public void testSaveChargeType() {
           log.debug("testing save...");

           final ChargeType chargeType = new ChargeType();
           // enter all required fields
           chargeType.setChargeTypeName("ViMcFsZuWfAxCmXaPaUwVdNePdSoXv");
           chargeType.setClevel(1L);
           chargeType.setDisabled(Boolean.FALSE);
           chargeType.setLeaf(Boolean.FALSE);
           
           // set expected behavior on dao
           context.checking(new Expectations() {{
               one(dao).save(with(same(chargeType)));
           }});

           manager.save(chargeType);
       }*/
    /*
     @Test
     public void testRemoveChargeType() {
     log.debug("testing remove...");

     final String chargeTypeId = "-11L";

     // set expected behavior on dao
     context.checking(new Expectations() {{
     one(dao).remove(with(equal(chargeTypeId)));
     }});

     manager.remove(chargeTypeId);
     }*/
}