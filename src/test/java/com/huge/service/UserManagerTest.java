package com.huge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.Constants;
import com.huge.ihos.system.systemManager.role.service.RoleManager;
import com.huge.ihos.system.systemManager.user.model.User;
import com.huge.ihos.system.systemManager.user.service.UserManager;

public class UserManagerTest
    extends BaseManagerTestCase {
    private Log log = LogFactory.getLog( UserManagerTest.class );

    @Autowired
    private UserManager mgr;

    @Autowired
    private RoleManager roleManager;

    private User user;

  //  @Test
    public void testGetUser()
        throws Exception {
        user = mgr.getUserByUsername( "user" );
        assertNotNull( user );

        log.debug( user );
        assertEquals( 1, user.getRoles().size() );
    }

    // @Test
    public void testSaveUser()
        throws Exception {
        user = mgr.getUserByUsername( "user" );
        //   user.setPhoneNumber("303-555-1212");
        user.setAccountLocked( true );
        log.debug( "saving user with updated phone number: " + user );

        user = mgr.saveUser( user );
        //  assertEquals("303-555-1212", user.getPhoneNumber());
        assertEquals( 1, user.getRoles().size() );
    }

    // @Test
    public void testAddAndRemoveUser()
        throws Exception {
        user = new User();

        // call populate method in super class to populate test data
        // from a properties file matching this class name
        user = (User) populate( user );

        user.addRole( roleManager.getRole( Constants.USER_ROLE ) );

        user = mgr.saveUser( user );
        assertEquals( "john", user.getUsername() );
        assertEquals( 1, user.getRoles().size() );

        log.debug( "removing user..." );

        mgr.removeUser( user.getId().toString() );

        try {
            user = mgr.getUserByUsername( "john" );
            fail( "Expected 'Exception' not thrown" );
        }
        catch ( Exception e ) {
            log.debug( e );
            assertNotNull( e );
        }
    }

    @Test
    public void testGetAll() {
        try {
            List<User> usersList = mgr.getAll();
            assertNotNull( usersList );
        }
        catch ( Exception e ) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
