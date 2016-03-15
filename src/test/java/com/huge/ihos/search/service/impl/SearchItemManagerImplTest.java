package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.SearchItemDao;
import com.huge.ihos.system.reportManager.search.model.SearchItem;
import com.huge.ihos.system.reportManager.search.service.impl.SearchItemManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SearchItemManagerImplTest
    extends BaseManagerMockTestCase {
    private SearchItemManagerImpl manager = null;

    private SearchItemDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SearchItemDao.class );
        manager = new SearchItemManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSearchItem() {
        log.debug( "testing get..." );

        final String searchItemId = "7L";
        final SearchItem searchItem = new SearchItem();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( searchItemId ) ) );
                will( returnValue( searchItem ) );
            }
        } );

        SearchItem result = manager.get( searchItemId );
        assertSame( searchItem, result );
    }

    @Test
    public void testGetSearchItems() {
        log.debug( "testing getAll..." );

        final List searchItems = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( searchItems ) );
            }
        } );

        List result = manager.getAll();
        assertSame( searchItems, result );
    }

   // @Test
    public void testSaveSearchItem() {
        log.debug( "testing save..." );

        final SearchItem searchItem = new SearchItem();
        // enter all required fields
        searchItem.setSearchFlag( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( searchItem ) ) );
            }
        } );

        manager.save( searchItem );
    }

    @Test
    public void testRemoveSearchItem() {
        log.debug( "testing remove..." );

        final String searchItemId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( searchItemId ) ) );
            }
        } );

        manager.remove( searchItemId );
    }
}