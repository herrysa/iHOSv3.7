package com.huge.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.systemManager.security.dao.LookupDao;

/**
 * This class tests the current LookupDao implementation class
 * @author mraible
 */
public class LookupDaoTest
    extends BaseDaoTestCase {
    @Autowired
    LookupDao lookupDao;

    @Test
    public void testGetRoles() {
        List roles = lookupDao.getRoles();
        log.debug( roles );
        assertTrue( roles.size() > 0 );
    }
}
