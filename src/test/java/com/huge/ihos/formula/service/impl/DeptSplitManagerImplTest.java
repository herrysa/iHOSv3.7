package com.huge.ihos.formula.service.impl;

import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.formula.dao.DeptSplitDao;
import com.huge.ihos.formula.model.DeptSplit;
import com.huge.service.impl.BaseManagerMockTestCase;

public class DeptSplitManagerImplTest
    extends BaseManagerMockTestCase {
    private DeptSplitManagerImpl manager = null;

    private DeptSplitDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DeptSplitDao.class );
        manager = new DeptSplitManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDeptSplit() {
        log.debug( "testing get..." );

        final Long deptSplitId = 7L;
        final DeptSplit deptSplit = new DeptSplit();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( deptSplitId ) ) );
                will( returnValue( deptSplit ) );
            }
        } );

        DeptSplit result = manager.get( deptSplitId );
        assertSame( deptSplit, result );
    }

    @Test
    public void testGetDeptSplits() {
        log.debug( "testing getAll..." );

        final List deptSplits = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( deptSplits ) );
            }
        } );

        List result = manager.getAll();
        assertSame( deptSplits, result );
    }

    @Test
    public void testSaveDeptSplit() {
        log.debug( "testing save..." );

        final DeptSplit deptSplit = new DeptSplit();
        // enter all required fields
        deptSplit.setCostratio( new BigDecimal( 0 ) );
        deptSplit.setDeptid( "RsMgNjVcWzUfHgOuOcUz" );
        deptSplit.setDeptname( "DxGdZfZnKnYeXsNeNtOgDdKzDyHhPoPnFlKrZaFpUvQiLiYjLn" );
        deptSplit.setDisabled( Boolean.FALSE );
        deptSplit.setPubdeptid( "MkCtOzUhYgHnTuCzDeRn" );
        deptSplit.setPubdeptname( "TdTlVcZsWkUvRrPjIpKqTnHsLdNvMiKpXpFnTqLbNpIkOnKxBo" );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( deptSplit ) ) );
            }
        } );

        manager.save( deptSplit );
    }

    @Test
    public void testRemoveDeptSplit() {
        log.debug( "testing remove..." );

        final Long deptSplitId = -11L;

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( deptSplitId ) ) );
            }
        } );

        manager.remove( deptSplitId );
    }
}