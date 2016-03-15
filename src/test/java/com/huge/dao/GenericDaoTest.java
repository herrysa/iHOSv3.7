package com.huge.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.hibernate.GenericDaoHibernate;
import com.huge.ihos.system.systemManager.user.model.User;

public class GenericDaoTest
    extends BaseDaoTestCase {
    Log log = LogFactory.getLog( GenericDaoTest.class );

    GenericDao<User, Long> genericDao;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setUp() {
        genericDao = new GenericDaoHibernate<User, Long>( User.class, sessionFactory );
    }

    /*@Test
    public void getUser() {
        User user = genericDao.get(-1L);
        assertNotNull(user);
        assertEquals("user", user.getUsername());
    }*/
    @Ignore
    public void getPyCodes() {
        String ss = genericDao.getPyCodes( "（）" );
        assertNotNull( ss );
        assertEquals( "（）", ss );
    }
    @Test
    public void getPyId() {
    	boolean ss = genericDao.existCodeEdit((long) 791, "t_acctcostmap", "acctMapId", "acctcode", "500101");
    	System.out.println(ss);
    }
}
