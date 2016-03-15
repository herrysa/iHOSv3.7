package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.SearchOptionDao;
import com.huge.ihos.system.reportManager.search.model.SearchOption;
import com.huge.ihos.system.reportManager.search.service.impl.SearchOptionManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SearchOptionManagerImplTest
    extends BaseManagerMockTestCase {
    private SearchOptionManagerImpl manager = null;

    private SearchOptionDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SearchOptionDao.class );
        manager = new SearchOptionManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSearchOption() {
        log.debug( "testing get..." );

        final String searchOptionId = "7L";
        final SearchOption searchOption = new SearchOption();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( searchOptionId ) ) );
                will( returnValue( searchOption ) );
            }
        } );

        SearchOption result = manager.get( searchOptionId );
        assertSame( searchOption, result );
    }

    @Test
    public void testGetSearchOptions() {
        log.debug( "testing getAll..." );

        final List searchOptions = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( searchOptions ) );
            }
        } );

        List result = manager.getAll();
        assertSame( searchOptions, result );
    }

   // @Test
    public void testSaveSearchOption() {
        log.debug( "testing save..." );

        final SearchOption searchOption = new SearchOption();
        // enter all required fields
        searchOption.setFieldName( "AuEfTaUlTjFnInAyJfQwMwEpKyHsMf" );
        searchOption.setPrintable( Boolean.FALSE );
        searchOption.setVisible( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( searchOption ) ) );
            }
        } );

        manager.save( searchOption );
    }

    @Test
    public void testRemoveSearchOption() {
        log.debug( "testing remove..." );

        final String searchOptionId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( searchOptionId ) ) );
            }
        } );

        manager.remove( searchOptionId );
    }
}