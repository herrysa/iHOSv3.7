package com.huge.ihos.search.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.reportManager.search.dao.SearchUrlDao;
import com.huge.ihos.system.reportManager.search.model.SearchUrl;
import com.huge.ihos.system.reportManager.search.service.impl.SearchUrlManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class SearchUrlManagerImplTest
    extends BaseManagerMockTestCase {
    private SearchUrlManagerImpl manager = null;

    private SearchUrlDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SearchUrlDao.class );
        manager = new SearchUrlManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSearchUrl() {
        log.debug( "testing get..." );

        final String searchUrlId = "7L";
        final SearchUrl searchUrl = new SearchUrl();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( searchUrlId ) ) );
                will( returnValue( searchUrl ) );
            }
        } );

        SearchUrl result = manager.get( searchUrlId );
        assertSame( searchUrl, result );
    }

    @Test
    public void testGetSearchUrls() {
        log.debug( "testing getAll..." );

        final List searchUrls = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( searchUrls ) );
            }
        } );

        List result = manager.getAll();
        assertSame( searchUrls, result );
    }

    @Test
    public void testSaveSearchUrl() {
        log.debug( "testing save..." );

        final SearchUrl searchUrl = new SearchUrl();
        // enter all required fields
        searchUrl.setNullFlag( Boolean.FALSE );
        searchUrl.setTitle( "WdQiOrJaReXlBmGrNeEcPbOlUdWjDkJuAmIkZrZuEjPuKrBlIa" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( searchUrl ) ) );
            }
        } );

        manager.save( searchUrl );
    }

    @Test
    public void testRemoveSearchUrl() {
        log.debug( "testing remove..." );

        final String searchUrlId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( searchUrlId ) ) );
            }
        } );

        manager.remove( searchUrlId );
    }
}