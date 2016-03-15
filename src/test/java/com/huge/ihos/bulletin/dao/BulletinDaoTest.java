package com.huge.ihos.bulletin.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.dao.BaseDaoTestCase;
import com.huge.ihos.system.oa.bulletin.dao.BulletinDao;

public class BulletinDaoTest extends BaseDaoTestCase {
    @Autowired
    private BulletinDao bulletinDao;

    @Test
    public void testDummy() {
    	log.info("here is a dummy test.");
      	Assert.assertTrue(true);
    }
}