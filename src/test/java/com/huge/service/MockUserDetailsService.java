package com.huge.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.huge.ihos.system.systemManager.user.model.User;

public class MockUserDetailsService
    implements UserDetailsService {
    public UserDetails loadUserByUsername( String username )
        throws UsernameNotFoundException, DataAccessException {
        return new User( "testuser" );
    }
}
