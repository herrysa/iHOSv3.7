package com.huge.ihos.formula.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.formula.service.UpItemManager;
import com.huge.service.BaseManagerTestCase;

public class UpItemManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private UpItemManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}