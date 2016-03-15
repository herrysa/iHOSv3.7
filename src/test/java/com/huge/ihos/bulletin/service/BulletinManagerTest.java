package com.huge.ihos.bulletin.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.oa.bulletin.service.BulletinManager;
import com.huge.service.BaseManagerTestCase;

public class BulletinManagerTest extends BaseManagerTestCase {
	@Autowired
    private BulletinManager manager;

    @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}