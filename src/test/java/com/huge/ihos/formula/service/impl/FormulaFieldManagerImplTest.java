package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.FormulaFieldDao;
import com.huge.ihos.formula.model.FormulaField;
import com.huge.service.impl.BaseManagerMockTestCase;

public class FormulaFieldManagerImplTest
    extends BaseManagerMockTestCase {
    private FormulaFieldManagerImpl manager = null;

    private FormulaFieldDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( FormulaFieldDao.class );
        manager = new FormulaFieldManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetFormulaField() {
        log.debug( "testing get..." );

        final String formulaFieldId = "7L";
        final FormulaField formulaField = new FormulaField();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( formulaFieldId ) ) );
                will( returnValue( formulaField ) );
            }
        } );

        FormulaField result = manager.get( formulaFieldId );
        assertSame( formulaField, result );
    }

    @Test
    public void testGetFormulaFields() {
        log.debug( "testing getAll..." );

        final List formulaFields = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( formulaFields ) );
            }
        } );

        List result = manager.getAll();
        assertSame( formulaFields, result );
    }

    @Test
    public void testSaveFormulaField() {
        log.debug( "testing save..." );

        final FormulaField formulaField = new FormulaField();
        // enter all required fields
        formulaField.setCalcType( "ToLdAfSoSgNiZsAtZuKwHhHkOsOuBxSjWdPkQjWpYgChRtTyYa" );
        formulaField.setDefaultValue( "FuOiQqYuNiVpDrJkUrSdVgFfHvXvPdZePrYwDsIpApFvLjXiZq" );
        formulaField.setDirection( "DjTwAuDzIpGqUnAfOeGvCtSzQiQnMbEfFsGkQlXjMvEkOjAeNe" );
        formulaField.setFieldName( "PxEbMmPoAkXaIsAySdRyBvEcYrOeAnOwGlNkLpFrWbJjJiFpXx" );
        formulaField.setFieldTitle( "VeKnKgZmZbMdYfPvEnAgHxFdOkNlXuUiSiFuGpKuXnLiKbFyGn" );
        formulaField.setInherited( Boolean.FALSE );
        formulaField.setKeyClass( "RfIdUwNlTsViPrFmFwErEnRhEtQiCvHfMnVnKnLaTbWeHnYzCg" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( formulaField ) ) );
            }
        } );

        manager.save( formulaField );
    }

    @Test
    public void testRemoveFormulaField() {
        log.debug( "testing remove..." );

        final String formulaFieldId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( formulaFieldId ) ) );
            }
        } );

        manager.remove( formulaFieldId );
    }
}