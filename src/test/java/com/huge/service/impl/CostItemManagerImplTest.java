package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.CostItemDao;
import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.inout.service.impl.CostItemManagerImpl;

public class CostItemManagerImplTest
    extends BaseManagerMockTestCase {
    private CostItemManagerImpl manager = null;

    private CostItemDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( CostItemDao.class );
        manager = new CostItemManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetCostItem() {
        log.debug( "testing get..." );

        final String costItemId = "7L";
        final CostItem costItem = new CostItem();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( costItemId ) ) );
                will( returnValue( costItem ) );
            }
        } );

        CostItem result = manager.get( costItemId );
        assertSame( costItem, result );
    }

    @Test
    public void testGetCostItems() {
        log.debug( "testing getAll..." );

        final List costItems = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( costItems ) );
            }
        } );

        List result = manager.getAll();
        assertSame( costItems, result );
    }

    /* @Test
     public void testSaveCostItem() {
         log.debug("testing save...");

         final CostItem costItem = new CostItem();
         // enter all required fields
         costItem.setBehaviour("LiXjDuAwXl");
         costItem.setClevel(2049037454);
         costItem.setControllable(Boolean.FALSE);
         costItem.setCostDesc("EeIxRiYtTcTlPsZeLzSqSkQvLuJoTrVqWcZyHtReIdIbEzCeHsOkEuNrFgUrDgUfFoMpSnJfVuDcToZrBmSyPhZyNcQzHzXdZbLo");
         costItem.setCostItemName("TrNwIkMtLrZyEpDmCnOzKuGmFnYeNa");
         costItem.setDisabled(Boolean.FALSE);
         costItem.setLeaf(Boolean.FALSE);
         
         // set expected behavior on dao
         context.checking(new Expectations() {{
             one(dao).save(with(same(costItem)));
         }});

         manager.save(costItem);
     }

     @Test
     public void testRemoveCostItem() {
         log.debug("testing remove...");

         final String costItemId = "-11L";

         // set expected behavior on dao
         context.checking(new Expectations() {{
             one(dao).remove(with(equal(costItemId)));
         }});

         manager.remove(costItemId);
     }*/
}