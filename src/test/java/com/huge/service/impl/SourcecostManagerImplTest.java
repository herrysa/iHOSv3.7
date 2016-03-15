package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.SourcecostDao;
import com.huge.ihos.inout.model.Sourcecost;
import com.huge.ihos.inout.service.impl.SourcecostManagerImpl;

public class SourcecostManagerImplTest
    extends BaseManagerMockTestCase {
    private SourcecostManagerImpl manager = null;

    private SourcecostDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SourcecostDao.class );
        manager = new SourcecostManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSourcecost() {
        log.debug( "testing get..." );

        final Long sourceCostId = 7L;
        final Sourcecost sourcecost = new Sourcecost();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( sourceCostId ) ) );
                will( returnValue( sourcecost ) );
            }
        } );

        Sourcecost result = manager.get( sourceCostId );
        assertSame( sourcecost, result );
    }

    @Test
    public void testGetSourcecosts() {
        log.debug( "testing getAll..." );

        final List sourcecosts = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( sourcecosts ) );
            }
        } );

        List result = manager.getAll();
        assertSame( sourcecosts, result );
    }

    //    @Test
    //    public void testSaveSourcecost() {
    //        log.debug("testing save...");
    //
    //        final Sourcecost sourcecost = new Sourcecost();
    //        // enter all required fields
    //        sourcecost.setCheckPeriod("IfPyBb");
    //        sourcecost.setIfallot(Boolean.FALSE);
    //        
    //        // set expected behavior on dao
    //        context.checking(new Expectations() {{
    //            one(dao).save(with(same(sourcecost)));
    //        }});
    //
    //        manager.save(sourcecost);
    //    }

    @Test
    public void testRemoveSourcecost() {
        log.debug( "testing remove..." );

        final Long sourceCostId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( sourceCostId ) ) );
            }
        } );

        manager.remove( sourceCostId );
    }
}