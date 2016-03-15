package com.huge.service.impl;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;
import com.huge.ihos.system.systemManager.organization.service.impl.DepartmentManagerImpl;

public class DepartmentManagerImplTest
    extends BaseManagerMockTestCase {
    private DepartmentManagerImpl manager = null;

    private DepartmentDao dao = null;

    @Before
    public void setUp() {
        dao = context.mock( DepartmentDao.class );
        manager = new DepartmentManagerImpl( dao );
    }

    @After
    public void tearDown() {
        manager = null;
    }

    @Test
    public void testGetDepartment() {
        log.debug( "testing get..." );

        final String departmentId = "7L";
        final Department department = new Department();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).get( with( equal( departmentId ) ) );
                will( returnValue( department ) );
            }
        } );

        Department result = manager.get( departmentId );
        assertSame( department, result );
    }

    @Test
    public void testGetDepartments() {
        log.debug( "testing getAll..." );

        final List departments = new ArrayList();

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).getAll();
                will( returnValue( departments ) );
            }
        } );

        List result = manager.getAll();
        assertSame( departments, result );
    }

    /*    @Test
     public void testSaveDepartment() {
     log.debug("testing save...");

     final Department department = new Department();
     // enter all required fields
     department.setDeptCode("RlSbKdYoAiKeJhWdQxMa");
     department.setName("WdPoOgEbTfIoHkBwZjTbMdHyOmMqYqWlZjSyUoEqHzNoPqVbNl");
    
     // set expected behavior on dao
     context.checking(new Expectations() {{
     one(dao).save(with(same(department)));
     }});

     manager.save(department);
     }*/

    @Test
    public void testRemoveDepartment() {
        log.debug( "testing remove..." );

        final String departmentId = "-11L";

        // set expected behavior on dao
        context.checking( new Expectations() {
            {
                one( dao ).remove( with( equal( departmentId ) ) );
            }
        } );

        manager.remove( departmentId );
    }
}