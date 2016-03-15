package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.AllotSetDao;
import com.huge.ihos.formula.model.AllotSet;
import com.huge.service.impl.BaseManagerMockTestCase;

public class AllotSetManagerImplTest
    extends BaseManagerMockTestCase {
    private AllotSetManagerImpl manager = null;

    private AllotSetDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( AllotSetDao.class );
        manager = new AllotSetManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetAllotSet() {
        log.debug( "testing get..." );

        final String allotSetId = "7L";
        final AllotSet allotSet = new AllotSet();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( allotSetId ) ) );
                will( returnValue( allotSet ) );
            }
        } );

        AllotSet result = manager.get( allotSetId );
        assertSame( allotSet, result );
    }

    @Test
    public void testGetAllotSets() {
        log.debug( "testing getAll..." );

        final List allotSets = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( allotSets ) );
            }
        } );

        List result = manager.getAll();
        assertSame( allotSets, result );
    }

    @Test
    public void testSaveAllotSet() {
        log.debug( "testing save..." );

        final AllotSet allotSet = new AllotSet();
        // enter all required fields
        allotSet.setAllotSetName( "UgDnAhRiKmQeHeTaWiHbNjRiBpKsYbBfItBrDcVqCtGnDuIzNcVlLeYlYwDpMvUoPoWaTtDmMwRfVsZaPkWqLpElVrDbJmPoHgTy" );
        allotSet.setDisabled( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( allotSet ) ) );
            }
        } );

        manager.save( allotSet );
    }

    @Test
    public void testRemoveAllotSet() {
        log.debug( "testing remove..." );

        final String allotSetId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( allotSetId ) ) );
            }
        } );

        manager.remove( allotSetId );
    }
}