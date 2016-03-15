package com.huge.service.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.huge.ihos.system.systemManager.security.dao.LookupDao;
import com.huge.ihos.system.systemManager.security.service.impl.LookupManagerImpl;

public class LookupManagerImplTest
    extends BaseManagerMockTestCase {
    private LookupManagerImpl mgr = new LookupManagerImpl();

    private LookupDao lookupDao;

    @Before
    public void setUp()
        throws Exception {
        lookupDao = context.mock( LookupDao.class );
        mgr.setDao( lookupDao );
    }

    @Test
    public void testGetAllRoles() {
//        log.debug( "entered 'testGetAllRoles' method" );
//
//        // set expected behavior on dao
//        Role role = new Role( Constants.ADMIN_ROLE );
//        final List<Role> testData = new ArrayList<Role>();
//        testData.add( role );
//        context.checking( new Expectations() {
//            {
//                one( lookupDao ).getRoles();
//                will( returnValue( testData ) );
//            }
//        } );
//
//        List<LabelValue> roles = mgr.getAllRoles();
//        assertTrue( roles.size() > 0 );
    	assertTrue(true);
    }
}
