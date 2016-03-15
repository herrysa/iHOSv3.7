package com.huge.ihos.indicatoranalysis.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.indicatoranalysis.service.IndicatorManager;
import com.huge.service.BaseManagerTestCase;

public class IndicatorManagerImplTest extends BaseManagerTestCase {
	@Autowired
    private IndicatorManager manager;

    @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}