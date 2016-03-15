package com.huge.ihos.inout.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.inout.service.SpecialItemManager;
import com.huge.service.BaseManagerTestCase;

public class SpecialItemManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private SpecialItemManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}