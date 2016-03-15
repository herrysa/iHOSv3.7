package com.huge.ihos.formula.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.formula.service.UpCostManager;
import com.huge.service.BaseManagerTestCase;

public class UpCostManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private UpCostManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}