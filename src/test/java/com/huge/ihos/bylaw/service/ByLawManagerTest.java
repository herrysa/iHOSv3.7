package com.huge.ihos.bylaw.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.system.oa.bylaw.service.ByLawManager;
import com.huge.service.BaseManagerTestCase;

public class ByLawManagerTest extends BaseManagerTestCase {
	 @Autowired
    private ByLawManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}