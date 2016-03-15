package com.huge.ihos.strategy.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.strategy.service.StrategyManager;
import com.huge.service.BaseManagerTestCase;

public class StrategyManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private StrategyManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}