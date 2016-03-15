package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.inout.dao.SourcepayinDao;
import com.huge.ihos.inout.model.Sourcepayin;
import com.huge.ihos.inout.service.impl.SourcepayinManagerImpl;

public class SourcepayinManagerImplTest
    extends BaseManagerMockTestCase {
    private SourcepayinManagerImpl manager = null;

    private SourcepayinDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( SourcepayinDao.class );
        manager = new SourcepayinManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetSourcepayin() {
        log.debug( "testing get..." );

        final Long sourcePayinId = 7L;
        final Sourcepayin sourcepayin = new Sourcepayin();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( sourcePayinId ) ) );
                will( returnValue( sourcepayin ) );
            }
        } );

        Sourcepayin result = manager.get( sourcePayinId );
        assertSame( sourcepayin, result );
    }

    @Test
    public void testGetSourcepayins() {
        log.debug( "testing getAll..." );

        final List sourcepayins = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( sourcepayins ) );
            }
        } );

        List result = manager.getAll();
        assertSame( sourcepayins, result );
    }

    //
    //    @Test
    //    public void testSaveSourcepayin() {
    //        log.debug("testing save...");
    //
    //        final Sourcepayin sourcepayin = new Sourcepayin();
    //        // enter all required fields
    //        sourcepayin.setAmount(null);
    //        sourcepayin.setCheckPeriod("UhVfYg");
    //        sourcepayin.setOutin("LoLvZp");
    //        
    //        // set expected behavior on dao
    //        context.checking(new Expectations() {{
    //            one(dao).save(with(same(sourcepayin)));
    //        }});
    //
    //        manager.save(sourcepayin);
    //    }

    @Test
    public void testRemoveSourcepayin() {
        log.debug( "testing remove..." );

        final Long sourcePayinId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( sourcePayinId ) ) );
            }
        } );

        manager.remove( sourcePayinId );
    }
}