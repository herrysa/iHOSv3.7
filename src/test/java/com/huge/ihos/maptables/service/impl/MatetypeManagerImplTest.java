package com.huge.ihos.maptables.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.datacollection.maptables.dao.MatetypeDao;
import com.huge.ihos.system.datacollection.maptables.model.Matetype;
import com.huge.ihos.system.datacollection.maptables.service.impl.MatetypeManagerImpl;
import com.huge.service.impl.BaseManagerMockTestCase;

public class MatetypeManagerImplTest
    extends BaseManagerMockTestCase {
    private MatetypeManagerImpl manager = null;

    private MatetypeDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( MatetypeDao.class );
        manager = new MatetypeManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    // @Test
    public void testGetMatetype() {
        log.debug( "testing get..." );

        final Long id = 7L;
        final Matetype matetype = new Matetype();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( id ) );
                will( returnValue( id ) );
            }
        } );

        Matetype result = manager.get( new Long( 7 ) );
        assertSame( matetype, result );
    }

    @Test
    public void testGetMatetypes() {
        log.debug( "testing getAll..." );

        final List matetypes = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( matetypes ) );
            }
        } );

        List result = manager.getAll();
        assertSame( matetypes, result );
    }

    @Test
    public void testSaveMatetype() {
        log.debug( "testing save..." );

        final Matetype matetype = new Matetype();
        // enter all required fields
        matetype.setMateType( "RxUoDaNbMdUmAxTbNiVcCmOiIdFxAxTvZvSxNxKtGuHkUwLjEvEkBoMaVoZr" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( matetype ) ) );
            }
        } );

        manager.save( matetype );
    }

    @Test
    public void testRemoveMatetype() {
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