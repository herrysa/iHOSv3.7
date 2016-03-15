package com.huge.ihos.inout.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.huge.ihos.inout.service.SpecialSourceManager;
import com.huge.service.BaseManagerTestCase;

public class SpecialSourceManagerImplTest extends BaseManagerTestCase {
	 @Autowired
    private SpecialSourceManager manager;

        @Test
    public void testDummy() throws Exception {
    	log.info("here is a dummy test.");
        Assert.assertTrue(true);
    }
}