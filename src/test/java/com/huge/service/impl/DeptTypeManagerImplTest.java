package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.systemManager.organization.dao.DeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;
import com.huge.ihos.system.systemManager.organization.service.impl.DeptTypeManagerImpl;

public class DeptTypeManagerImplTest
    extends BaseManagerMockTestCase {
    private DeptTypeManagerImpl manager = null;

    private DeptTypeDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DeptTypeDao.class );
        manager = new DeptTypeManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDeptType() {
        log.debug( "testing get..." );

        final String deptTypeId = "7L";
        final DeptType deptType = new DeptType();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( deptTypeId ) ) );
                will( returnValue( deptType ) );
            }
        } );

        DeptType result = manager.get( deptTypeId );
        assertSame( deptType, result );
    }

    @Test
    public void testGetDeptTypes() {
        log.debug( "testing getAll..." );

        final List deptTypes = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( deptTypes ) );
            }
        } );

        List result = manager.getAll();
        assertSame( deptTypes, result );
    }

    @Test
    public void testSaveDeptType() {
        log.debug( "testing save..." );

        final DeptType deptType = new DeptType();
        // enter all required fields
        deptType.setDeptTypeName( "ErSnAxWsGwIvPzEmMiIj" );
        deptType.setDisabled( Boolean.FALSE );

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).save( with( same( deptType ) ) );
            }
        } );

        manager.save( deptType );
    }

    @Test
    public void testRemoveDeptType() {
        log.debug( "testing remove..." );

        final String deptTypeId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( deptTypeId ) ) );
            }
        } );

        manager.remove( deptTypeId );
    }
}