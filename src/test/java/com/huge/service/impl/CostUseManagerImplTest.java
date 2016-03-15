package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.CostUseDao;
import com.huge.ihos.inout.model.CostUse;
import com.huge.ihos.inout.service.impl.CostUseManagerImpl;

public class CostUseManagerImplTest
    extends BaseManagerMockTestCase {
    private CostUseManagerImpl manager = null;

    private CostUseDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( CostUseDao.class );
        manager = new CostUseManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetCostUse() {
        log.debug( "testing get..." );

        final String costUseId = "11";
        final CostUse costUse = new CostUse();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( costUseId ) ) );
                will( returnValue( costUse ) );
            }
        } );

        CostUse result = manager.get( costUseId );
        assertSame( costUse, result );
    }

    @Test
    public void testGetCostUses() {
        log.debug( "testing getAll..." );

        final List costUses = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( costUses ) );
            }
        } );

        List result = manager.getAll();
        assertSame( costUses, result );
    }

    @Test
    public void testSaveCostUse() {
        log.debug( "testing save..." );

        final CostUse costUse = new CostUse();
        // enter all required fields
        costUse.setCostUseName( "AtHeSjKwUeZzQrIjZjKz" );
        costUse.setDisabled( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( costUse ) ) );
            }
        } );

        manager.save( costUse );
    }

    /*    @Test
     public void testRemoveCostUse() {
     log.debug("testing remove...");

     final String costUseId = "11";

     // set expected behavior on dao
     context.checking(new Expectations() {{
     one(dao).remove(with(equal(costUseId)));
     }});

     manager.remove(costUseId);
     }*/
}