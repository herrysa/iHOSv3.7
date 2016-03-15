package com.huge.service;

import static org.junit.Assert.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.systemManager.organization.service.DepartmentManager;

public class DepartmentManagerTest
    extends BaseManagerTestCase {
    private Log log = LogFactory.getLog( DepartmentManagerTest.class );

    @Autowired
    DepartmentManager departmentManager;

    @Test
    public void testGetFullDepartmentList() {
        Object obj = departmentManager.getFullDepartmentList();
        assertTrue( obj != null );
    }
}
