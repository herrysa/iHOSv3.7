package com.huge.ihos.indicatoranalysis.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.indicatoranalysis.service.IndicatorAnalysisManager;
import com.huge.service.BaseManagerTestCase;

public class IndicatorAnalysisManagerImplTest extends BaseManagerTestCase {
	@Autowired
    private IndicatorAnalysisManager manager;

    @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}