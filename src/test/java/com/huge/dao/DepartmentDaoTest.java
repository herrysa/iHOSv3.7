package com.huge.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.systemManager.organization.dao.DepartmentDao;
import com.huge.ihos.system.systemManager.organization.model.Department;

public class DepartmentDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DepartmentDao departmentDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDepartment() {
        Department department = new Department();

        // enter all required fields
        department.setDeptCode( "CyQeOoRbGsJyJnNoQdAa" );
        department.setName( "ObVmQgRxNeOkWnEfLuGaAhJaTfUiGqLvAtAmSuUmEuZsBvIyXd" );

        log.debug( "adding department..." );
        department = departmentDao.save( department );

        department = departmentDao.get( department.getDepartmentId() );

        assertNotNull( department.getDepartmentId() );

        log.debug( "removing department..." );

        departmentDao.remove( department.getDepartmentId() );

        // should throw DataAccessException 
        departmentDao.get( department.getDepartmentId() );
    }

   // @Test
    public void testGetFullRoot() {
        List roots = departmentDao.getAllRoot();
        assertTrue( roots.size() > 0 );
    }

  //  @Test
    public void getAllSubByParent() {
        List roots = departmentDao.getAllRoot();
        Department root = (Department) roots.get( 0 );
        List subs = this.departmentDao.getAllSubByParent( root.getDepartmentId() );

        assertTrue( subs.size() > 0 );
    }

}