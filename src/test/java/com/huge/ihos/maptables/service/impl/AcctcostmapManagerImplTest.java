package com.huge.ihos.maptables.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.maptables.dao.AcctcostmapDao;
import com.huge.ihos.system.datacollection.maptables.model.Acctcostmap;
import com.huge.ihos.system.datacollection.maptables.service.impl.AcctcostmapManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class AcctcostmapManagerImplTest
    extends BaseManagerMockTestCase {
    private AcctcostmapManagerImpl manager = null;

    private AcctcostmapDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( AcctcostmapDao.class );
        manager = new AcctcostmapManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetAcctcostmap() {
        log.debug( "testing get..." );

        final Long id = 7L;
        final Acctcostmap acctcostmap = new Acctcostmap();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( id ) );
                will( returnValue( acctcostmap ) );
            }
        } );

        Acctcostmap result = manager.get( id );
        assertSame( acctcostmap, result );
    }

    @Test
    public void testGetAcctcostmaps() {
        log.debug( "testing getAll..." );

        final List acctcostmaps = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( acctcostmaps ) );
            }
        } );

        List result = manager.getAll();
        assertSame( acctcostmaps, result );
    }

    @Test
    public void testSaveAcctcostmap() {
        log.debug( "testing save..." );

        final Acctcostmap acctcostmap = new Acctcostmap();
        // enter all required fields

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( acctcostmap ) ) );
            }
        } );

        manager.save( acctcostmap );
    }

    @Test
    public void testRemoveAcctcostmap() {
        log.debug( "testing remove..." );

        final Long id = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( id ) );
            }
        } );

        manager.remove( id );
    }
}