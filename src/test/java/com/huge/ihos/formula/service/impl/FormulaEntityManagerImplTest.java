package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.FormulaEntityDao;
import com.huge.ihos.formula.model.FormulaEntity;
import com.huge.service.impl.BaseManagerMockTestCase;

public class FormulaEntityManagerImplTest
    extends BaseManagerMockTestCase {
    private FormulaEntityManagerImpl manager = null;

    private FormulaEntityDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( FormulaEntityDao.class );
        manager = new FormulaEntityManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetFormulaEntity() {
        log.debug( "testing get..." );

        final String formulaEntityId = "7L";
        final FormulaEntity formulaEntity = new FormulaEntity();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( formulaEntityId ) ) );
                will( returnValue( formulaEntity ) );
            }
        } );

        FormulaEntity result = manager.get( formulaEntityId );
        assertSame( formulaEntity, result );
    }

    @Test
    public void testGetFormulaEntities() {
        log.debug( "testing getAll..." );

        final List formulaEntities = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( formulaEntities ) );
            }
        } );

        List result = manager.getAll();
        assertSame( formulaEntities, result );
    }

    @Test
    public void testSaveFormulaEntity() {
        log.debug( "testing save..." );

        final FormulaEntity formulaEntity = new FormulaEntity();
        // enter all required fields
        formulaEntity.setEntityName( "NyDzLoFxXxLzEjRdWyTcKqJrZnWbRfGgMdKpVjJhRtDhBlBcPm" );
        formulaEntity.setTableName( "AaZcGrBcHtWkNiDhWlAjTpGuAfDbIbAsBcEjJfOpRcJsAxKuZf" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( formulaEntity ) ) );
            }
        } );

        manager.save( formulaEntity );
    }

    @Test
    public void testRemoveFormulaEntity() {
        log.debug( "testing remove..." );

        final String formulaEntityId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( formulaEntityId ) ) );
            }
        } );

        manager.remove( formulaEntityId );
    }
}