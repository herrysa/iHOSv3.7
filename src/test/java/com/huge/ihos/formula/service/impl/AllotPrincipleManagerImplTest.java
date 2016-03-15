package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.AllotPrincipleDao;
import com.huge.ihos.formula.model.AllotPrinciple;
import com.huge.service.impl.BaseManagerMockTestCase;

public class AllotPrincipleManagerImplTest
    extends BaseManagerMockTestCase {
    private AllotPrincipleManagerImpl manager = null;

    private AllotPrincipleDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( AllotPrincipleDao.class );
        manager = new AllotPrincipleManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetAllotPrinciple() {
        log.debug( "testing get..." );

        final String allotPrincipleId = "7L";
        final AllotPrinciple allotPrinciple = new AllotPrinciple();

        // set expected behavior on dao 
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( allotPrincipleId ) ) );
                will( returnValue( allotPrinciple ) );
            }
        } );

        AllotPrinciple result = manager.get( allotPrincipleId );
        assertSame( allotPrinciple, result );
    }

    @Test
    public void testGetAllotPrinciples() {
        log.debug( "testing getAll..." );

        final List allotPrinciples = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( allotPrinciples ) );
            }
        } );

        List result = manager.getAll();
        assertSame( allotPrinciples, result );
    }

    @Test
    public void testSaveAllotPrinciple() {
        log.debug( "testing save..." );

        final AllotPrinciple allotPrinciple = new AllotPrinciple();
        // enter all required fields
        allotPrinciple.setAllotPrincipleName( "NaGnWvAlWoViUnVjOgXuOaOnOsNtZrWhJbRhKaUcPyEnKvWkBjVmJkEqJoHqUjQuLpFdAuWaEjUzKpPxWgEoHlSzQyOmUtAqPjAk" );
        allotPrinciple.setDisabled( Boolean.FALSE );
        allotPrinciple.setParam1( "UcKsAoFlQrJkYoHlXgNaLoSrSzRwYeWpUtEeFhCcQfCmYbKfEhUxOwChYnQrPiYrRsOgByRfXbSdOoBrJfQvZuOdQbEeZlPrOwRh" );
        allotPrinciple.setParamed( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( allotPrinciple ) ) );
            }
        } );

        manager.save( allotPrinciple );
    }

    @Test
    public void testRemoveAllotPrinciple() {
        log.debug( "testing remove..." );

        final String allotPrincipleId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( allotPrincipleId ) ) );
            }
        } );

        manager.remove( allotPrincipleId );
    }
}