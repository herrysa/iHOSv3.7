package com.huge.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;

import com.huge.ihos.system.systemManager.organization.dao.DeptTypeDao;
import com.huge.ihos.system.systemManager.organization.model.DeptType;

public class DeptTypeDaoTest
    extends BaseDaoTestCase {
    @Autowired
    private DeptTypeDao deptTypeDao;

    @Test
    @ExpectedException( DataAccessException.class )
    public void testAddAndRemoveDeptType() {
        DeptType deptType = new DeptType();

        // enter all required fields
        deptType.setDeptTypeName( "GxZjOkOmJkWfBzOaYpDt" );
        deptType.setDisabled( Boolean.FALSE );

        log.debug( "adding deptType..." );
        deptType = deptTypeDao.save( deptType );

        deptType = deptTypeDao.get( deptType.getDeptTypeId() );

        assertNotNull( deptType.getDeptTypeId() );

        log.debug( "removing deptType..." );

        deptTypeDao.remove( deptType.getDeptTypeId() );

        // should throw DataAccessException 
        deptTypeDao.get( deptType.getDeptTypeId() );
    }
}