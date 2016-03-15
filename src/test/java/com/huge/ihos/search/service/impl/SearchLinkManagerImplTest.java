package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.SearchLinkDao;
import com.huge.ihos.system.reportManager.search.model.SearchLink;
import com.huge.ihos.system.reportManager.search.service.impl.SearchLinkManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SearchLinkManagerImplTest
    extends BaseManagerMockTestCase {
    private SearchLinkManagerImpl manager = null;

    private SearchLinkDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SearchLinkDao.class );
        manager = new SearchLinkManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSearchLink() {
        log.debug( "testing get..." );

        final String searchLinkId = "7L";
        final SearchLink searchLink = new SearchLink();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( searchLinkId ) ) );
                will( returnValue( searchLink ) );
            }
        } );

        SearchLink result = manager.get( searchLinkId );
        assertSame( searchLink, result );
    }

    @Test
    public void testGetSearchLinks() {
        log.debug( "testing getAll..." );

        final List searchLinks = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( searchLinks ) );
            }
        } );

        List result = manager.getAll();
        assertSame( searchLinks, result );
    }

    @Test
    public void testSaveSearchLink() {
        log.debug( "testing save..." );

        final SearchLink searchLink = new SearchLink();
        // enter all required fields
        searchLink.setLink( "MjPkYhAhQhOcHrEqFxSeBlUmLeKuUhIzDpQtJcBzPzBlCeCmHc" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( searchLink ) ) );
            }
        } );

        manager.save( searchLink );
    }

    @Test
    public void testRemoveSearchLink() {
        log.debug( "testing remove..." );

        final String searchLinkId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( searchLinkId ) ) );
            }
        } );

        manager.remove( searchLinkId );
    }
}