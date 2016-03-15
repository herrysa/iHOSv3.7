package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.AllotItemDao;
import com.huge.ihos.formula.model.AllotItem;
import com.huge.ihos.inout.model.CostItem;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.service.impl.BaseManagerMockTestCase;

public class AllotItemManagerImplTest
    extends BaseManagerMockTestCase {
    private AllotItemManagerImpl manager = null;

    private AllotItemDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( AllotItemDao.class );
        manager = new AllotItemManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetAllotItem() {
        log.debug( "testing get..." );

        final Long allotItemId = 7L;
        final AllotItem allotItem = new AllotItem();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( allotItemId ) ) );
                will( returnValue( allotItem ) );
            }
        } );

        AllotItem result = manager.get( allotItemId );
        assertSame( allotItem, result );
    }

    @Test
    public void testGetAllotItems() {
        log.debug( "testing getAll..." );

        final List allotItems = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( allotItems ) );
            }
        } );

        List result = manager.getAll();
        assertSame( allotItems, result );
    }

  //  @Test
    public void testSaveAllotItem() {
        log.debug( "testing save..." );

        final AllotItem allotItem = new AllotItem();
        // enter all required fields
        allotItem.setAllotCost( new BigDecimal( 0 ) );
        allotItem.setCheckPeriod( "IhJoVu" );

        allotItem.setDepartment( new Department() );
        allotItem.setCostItem( new CostItem() );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( allotItem ) ) );
            }
        } );

        manager.save( allotItem );
    }

    @Test
    public void testRemoveAllotItem() {
        log.debug( "testing remove..." );

        final Long allotItemId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( allotItemId ) ) );
            }
        } );

        manager.remove( allotItemId );
    }
}