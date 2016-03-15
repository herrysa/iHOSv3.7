package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.PayinItemDao;
import com.huge.ihos.inout.model.PayinItem;
import com.huge.ihos.inout.service.impl.PayinItemManagerImpl;

public class PayinItemManagerImplTest
    extends BaseManagerMockTestCase {
    private PayinItemManagerImpl manager = null;

    private PayinItemDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( PayinItemDao.class );
        manager = new PayinItemManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetPayinItem() {
        log.debug( "testing get..." );

        final String payinItemId = "7L";
        final PayinItem payinItem = new PayinItem();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( payinItemId ) ) );
                will( returnValue( payinItem ) );
            }
        } );

        PayinItem result = manager.get( payinItemId );
        assertSame( payinItem, result );
    }

    @Test
    public void testGetPayinItems() {
        log.debug( "testing getAll..." );

        final List payinItems = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( payinItems ) );
            }
        } );

        List result = manager.getAll();
        assertSame( payinItems, result );
    }

    /*   @Test
       public void testSavePayinItem() {
           log.debug("testing save...");

           final PayinItem payinItem = new PayinItem();
           // enter all required fields
           payinItem.setDisabled(Boolean.FALSE);
           payinItem.setPayinItemName("BtWnCjTsSbWhDhRkPsQcJjYkGjXoKe");
           
           // set expected behavior on dao
           context.checking(new Expectations() {{
               one(dao).save(with(same(payinItem)));
           }});

           manager.save(payinItem);
       }*/

    /*  @Test
      public void testRemovePayinItem() {
          log.debug("testing remove...");

          final String payinItemId = "-11L";

          // set expected behavior on dao
          context.checking(new Expectations() {{
              one(dao).remove(with(equal(payinItemId)));
          }});

          manager.remove(payinItemId);
      }*/
}